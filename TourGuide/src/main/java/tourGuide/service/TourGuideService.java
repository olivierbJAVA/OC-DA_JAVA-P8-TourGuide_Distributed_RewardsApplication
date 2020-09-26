package tourGuide.service;

import java.util.*;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//Problème import Modules
import gpsModule.service.IGpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import preferencesModule.service.IPreferencesService;
import rewardModule.service.IRewardsService;
import tourGuide.configuration.TourGuideInitialization;
import tourGuide.domain.NearbyAttraction;
import tourGuide.tracker.Tracker;
import tourGuide.domain.User;
import preferencesModule.domain.UserPreferences;
import rewardModule.domain.UserReward;
import tripPricer.Provider;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final IGpsService gpsService;
	private final IRewardsService rewardsService;
	private final IPreferencesService preferencesService;
	public final Tracker tracker;
	boolean testMode = true;

	// Mieux si passé en paramètre du constructeur ?
	//@Autowired
	private TourGuideInitialization init = new TourGuideInitialization();

	public TourGuideService(IGpsService gpsService, IRewardsService rewardsService, IPreferencesService preferencesService) {
		this.gpsService = gpsService;
		this.rewardsService = rewardsService;
		this.preferencesService = preferencesService;

		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			init.initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this, rewardsService);
		addShutDownHook();
	}

	public User getUser(String userName) {
		return init.getInternalUserMap().get(userName);
	}

	public List<User> getAllUsers() {
		return  init.getInternalUserMap().values().stream().collect(Collectors.toList());
	}

	public void addUser(User user) {
		if(!init.getInternalUserMap().containsKey(user.getUserName())) {
			init.getInternalUserMap().put(user.getUserName(), user);
		}
	}

	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}

	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ?
			user.getLastVisitedLocation() :
			trackUserLocation(user);
		return visitedLocation;
	}

	public HashMap<String, Location> getAllCurrentLocations() {
		HashMap<String, Location> allCurrentLocations = new HashMap<>();
		List<User> allUsers = getAllUsers();
		allUsers.forEach(user -> allCurrentLocations.put(user.getUserId().toString(), user.getLastVisitedLocation().location));
		return allCurrentLocations;
	}
	// Appel preferencesService.getPrice
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = preferencesService.getPrice(init.getTripPricerApiKey(), user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	// Appel gpsService.getUserLocation
	public VisitedLocation trackUserLocation(User user) {
		VisitedLocation visitedLocation = gpsService.getUserLocation(user.getUserId());
		user.addToVisitedLocations(visitedLocation);
		//rewardsService.calculateRewards(user);
		return visitedLocation;
	}
	// Appel gpsService.getAttractions & rewardsService.getDistance & rewardsService.getRewardPoints
	public List<NearbyAttraction> getNearByAttractions(VisitedLocation visitedLocation, User user) {
		List<NearbyAttraction> nearbyAttractions = new ArrayList<>();
		List<Attraction> allAttractions = gpsService.getAttractions();
		TreeMap<Double, NearbyAttraction> treeAttractionDistance = new TreeMap<>();
		allAttractions.forEach(attraction -> treeAttractionDistance.put(rewardsService.getDistance(attraction, visitedLocation.location), new NearbyAttraction(attraction.attractionName, new Location(attraction.latitude, attraction.longitude), visitedLocation.location, rewardsService.getDistance(attraction, visitedLocation.location), rewardsService.getRewardPoints(attraction, user))));
		nearbyAttractions = treeAttractionDistance.values().stream()
															.limit(5)
															.collect(Collectors.toList());
		return nearbyAttractions;
	}

	public UserPreferences getUserPreferences(User user) {
		UserPreferences userPreferences = user.getUserPreferences();
		return userPreferences;
	}

	public UserPreferences postUserPreferences(User user, UserPreferences userPreferences) {
		user.setUserPreferences(userPreferences);
		return userPreferences;
	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
}
