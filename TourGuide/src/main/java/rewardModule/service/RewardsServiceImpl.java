package rewardModule.service;

import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

import java.util.UUID;

/**
 * Class in charge of managing the services for the Rewards service.
 */
@Service
public class RewardsServiceImpl implements IRewardsService {

	private final RewardCentral rewardsCentral;
	
	public RewardsServiceImpl(RewardCentral rewardCentral) {
		this.rewardsCentral = rewardCentral;
	}

	/**
	 * Return the rewards points for an attraction and a given user.
	 *
	 * @param attractionId The id of the attraction
	 * @param userId The id of the user
	 * @return The number of rewards points earned
	 */
	@Override
	public int getRewardPoints(UUID attractionId, UUID userId) {
		return rewardsCentral.getAttractionRewardPoints(attractionId, userId);
	}

}
