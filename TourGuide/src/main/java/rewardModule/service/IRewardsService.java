package rewardModule.service;

import java.util.UUID;

/**
 * Interface to be implemented for managing the services for the Rewards service.
 */
public interface IRewardsService {

    /**
     * Return the rewards points for an attraction and a given user.
     *
     * @param attractionId The id of the attraction
     * @param userId The id of the user
     * @return The number of rewards points earned
     */
    int getRewardPoints(UUID attractionId, UUID userId);

}
