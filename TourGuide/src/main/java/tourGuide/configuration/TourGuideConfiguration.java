package tourGuide.configuration;

import gpsModule.service.GpsServiceImpl;
import gpsModule.service.IGpsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import preferencesModule.service.IPreferencesService;
import preferencesModule.service.PreferencesServiceImpl;
import rewardCentral.RewardCentral;
import rewardModule.service.IRewardsService;
import rewardModule.service.RewardsServiceImpl;
import tripPricer.TripPricer;

@Configuration
public class TourGuideConfiguration {
	
	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

	@Bean
	public IGpsService getGpsService() {
		return new GpsServiceImpl(getGpsUtil());
	}

	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

	@Bean
	public IRewardsService getRewardsService() {
		return new RewardsServiceImpl(getGpsService(), getRewardCentral());
	}

	@Bean
	public TripPricer getTripPricer() {
		return new TripPricer();
	}

	@Bean
	public IPreferencesService getPreferencesService() {
		return new PreferencesServiceImpl(getTripPricer());
	}



	@Bean
	public TourGuideInitialization getTourGuideInitialization() {
		return new TourGuideInitialization();
	}
}
