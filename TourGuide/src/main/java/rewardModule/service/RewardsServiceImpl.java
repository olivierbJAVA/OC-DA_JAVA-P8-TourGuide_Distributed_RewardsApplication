package rewardModule.service;

import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardsServiceImpl implements IRewardsService {

	private final RewardCentral rewardsCentral;
	
	public RewardsServiceImpl(RewardCentral rewardCentral) {
		this.rewardsCentral = rewardCentral;
	}

	@Override
	public int getRewardPoints(UUID attractionId, UUID userId) {
		return rewardsCentral.getAttractionRewardPoints(attractionId, userId);
	}

}
