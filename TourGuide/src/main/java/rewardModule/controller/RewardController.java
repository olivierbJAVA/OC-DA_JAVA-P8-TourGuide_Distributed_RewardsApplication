package rewardModule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rewardModule.service.IRewardsService;

import java.util.UUID;

@RestController
public class RewardController {
    private Logger logger = LoggerFactory.getLogger(RewardController.class);

    private final IRewardsService rewardsService;

    public RewardController(IRewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/getRewardPoints")
    public int getRewardPoints(@RequestParam String attractionId, @RequestParam String userId) {
        logger.debug("Request getRewardPoints");
        int rewardsPoints = rewardsService.getRewardPoints(UUID.fromString(attractionId), UUID.fromString(userId));
        logger.debug("Response rewardsPoints=" + rewardsPoints);
        return rewardsPoints;
    }
}