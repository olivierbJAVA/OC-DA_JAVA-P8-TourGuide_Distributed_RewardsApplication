package rewardModule.configuration;

// Problème import dans un autre Module
import gpsModule.service.GpsServiceImpl;
import gpsModule.service.IGpsService;
import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;
import rewardModule.service.IRewardsService;
import rewardModule.service.RewardsServiceImpl;

@Configuration
public class RewardConfiguration {
	
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
}
