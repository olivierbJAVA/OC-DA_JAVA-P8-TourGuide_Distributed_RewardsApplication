package rewardModule.controller;

import com.jsoniter.output.JsonStream;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
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

    @RequestMapping("/getRewardPoints")
    public String getRewardPoints(Attraction attraction, User user) {
        return JsonStream.serialize(rewardsService.getRewardPoints(attraction, user));
    }

    @RequestMapping("/getDistance")
    public String getDistance(Location loc1, Location loc2) {
        return JsonStream.serialize(rewardsService.getDistance(loc1, loc2));
    }
}