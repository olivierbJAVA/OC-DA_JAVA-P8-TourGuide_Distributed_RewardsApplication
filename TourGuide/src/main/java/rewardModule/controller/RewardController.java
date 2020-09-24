package rewardModule.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rewardModule.service.IRewardsService;
import tourGuide.domain.User;

@RestController
public class RewardController {

    @Autowired
    IRewardsService rewardsService;

    @RequestMapping("/getPrice")
    public void calculateRewards(User user) {
        rewardsService.calculateRewards(user);
    }
}
