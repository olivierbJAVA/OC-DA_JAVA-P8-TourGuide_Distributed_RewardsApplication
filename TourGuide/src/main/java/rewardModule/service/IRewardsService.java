package rewardModule.service;

import java.util.UUID;

public interface IRewardsService {

    int getRewardPoints(UUID attractionId, UUID userId);

}
