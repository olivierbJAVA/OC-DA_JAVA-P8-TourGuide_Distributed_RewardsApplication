package rewardModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rewardModule.service.IRewardsService;

import java.util.UUID;

@RestController
public class RewardController {

    @Autowired
    IRewardsService rewardsService;

    @RequestMapping("/getRewardPoints")
    public int getRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
        return rewardsService.getRewardPoints(attractionId, userId);
    }

}