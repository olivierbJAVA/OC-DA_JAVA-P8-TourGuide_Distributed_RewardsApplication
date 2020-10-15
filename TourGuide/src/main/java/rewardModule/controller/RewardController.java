package rewardModule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rewardModule.service.IRewardsService;

import java.util.UUID;

/**
 * Controller in charge of managing the endpoints for the Rewards service.
 */
@RestController
public class RewardController {
    private Logger logger = LoggerFactory.getLogger(RewardController.class);

    private final IRewardsService rewardsService;

    public RewardController(IRewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * Method managing the GET "/getRewardPoints" endpoint HTTP request to get the rewards points for an attraction and a given user.
     *
     * @param attractionId The id of the attraction
     * @param userId The id of the user
     * @return The number of rewards points earned
     */
    @GetMapping("/getRewardPoints")
    public int getRewardPoints(@RequestParam String attractionId, @RequestParam String userId) {
        logger.debug("Request getRewardPoints");
        int rewardsPoints = rewardsService.getRewardPoints(UUID.fromString(attractionId), UUID.fromString(userId));
        logger.debug("Response rewardsPoints=" + rewardsPoints);
        return rewardsPoints;
    }
}