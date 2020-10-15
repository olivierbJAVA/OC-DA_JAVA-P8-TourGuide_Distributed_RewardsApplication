package rewardModule.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

/**
 * Configuration class of the Rewards service.
 */
@Configuration
public class RewardConfiguration {

	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

}
