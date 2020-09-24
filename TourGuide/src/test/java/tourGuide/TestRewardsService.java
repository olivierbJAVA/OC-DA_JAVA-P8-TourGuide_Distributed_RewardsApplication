package tourGuide;

import static org.junit.Assert.*;

import java.util.*;

import gpsModule.service.GpsServiceImpl;
import gpsModule.service.IGpsService;
import gpsUtil.location.Location;
import org.junit.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import preferencesModule.service.IPreferencesService;
import preferencesModule.service.PreferencesServiceImpl;
import rewardCentral.RewardCentral;
import rewardModule.service.IRewardsService;
import tourGuide.helper.InternalTestHelper;
import rewardModule.service.RewardsServiceImpl;
import tourGuide.service.TourGuideService;
import tourGuide.domain.User;
import rewardModule.domain.UserReward;
import tripPricer.TripPricer;
import utils.TourGuideTestUtil;

public class TestRewardsService {

	@Test
	public void calculateRewards() {
		//Added to fix NumberFormatException due to decimal number separator
		Locale.setDefault(new Locale("en", "US"));

		// ARRANGE
		InternalTestHelper.setInternalUserNumber(0);
		IGpsService gpsService = new GpsServiceImpl(new GpsUtil());
		IRewardsService rewardsService = new RewardsServiceImpl(gpsService, new RewardCentral());
		IPreferencesService preferencesService = new PreferencesServiceImpl(new TripPricer());
		TourGuideService tourGuideService = new TourGuideService(gpsService, rewardsService, preferencesService);
		tourGuideService.tracker.stopTracking();

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsService.getAttractions().get(0);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));

		// ACT
		rewardsService.calculateRewards(user);

		// ASSERT
		List<UserReward> userRewards = user.getUserRewards();

		assertEquals(1, userRewards.size());
	}
	
	@Test
	public void isWithinAttractionProximity() {
		// ARRANGE
		IGpsService gpsService = new GpsServiceImpl(new GpsUtil());
		IRewardsService rewardsService = new RewardsServiceImpl(gpsService, new RewardCentral());

		Attraction attraction = gpsService.getAttractions().get(0);

		// ACT & ASSERT
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
	}

	@Test
	public void nearAttraction() {
		// ARRANGE
		IGpsService gpsService = new GpsServiceImpl(new GpsUtil());
		IRewardsService rewardsService = new RewardsServiceImpl(gpsService, new RewardCentral());
		rewardsService.setProximityBuffer(Integer.MAX_VALUE);
		Attraction attraction = gpsService.getAttractions().get(0);

		VisitedLocation visitedLocationRandom = new VisitedLocation(UUID.randomUUID(), new Location(TourGuideTestUtil.generateRandomLatitude(), TourGuideTestUtil.generateRandomLongitude()), TourGuideTestUtil.getRandomTime());

		// ACT & ASSERT
		assertTrue(rewardsService.nearAttraction(visitedLocationRandom, attraction));
	}

	//@Ignore // Needs fixed - can throw ConcurrentModificationException
	@Test
	public void nearAllAttractions() {
		// ARRANGE
		InternalTestHelper.setInternalUserNumber(1);
		IGpsService gpsService = new GpsServiceImpl(new GpsUtil());
		IRewardsService rewardsService = new RewardsServiceImpl(gpsService, new RewardCentral());
		rewardsService.setProximityBuffer(Integer.MAX_VALUE);
		IPreferencesService preferencesService = new PreferencesServiceImpl(new TripPricer());
		TourGuideService tourGuideService = new TourGuideService(gpsService, rewardsService, preferencesService);
		tourGuideService.tracker.stopTracking();

		// ACT
		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));

		// ASSERT
		assertEquals(gpsService.getAttractions().size(), userRewards.size());
	}

}
