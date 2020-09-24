package rewardModule.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.domain.User;

public interface IRewardsService {

    void setProximityBuffer(int proximityBuffer);

    void setDefaultProximityBuffer();

    void calculateRewards(User user);

    int getRewardPoints(Attraction attraction, User user);

    boolean isWithinAttractionProximity(Attraction attraction, Location location);

    boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction);

    double getDistance(Location loc1, Location loc2);

}
