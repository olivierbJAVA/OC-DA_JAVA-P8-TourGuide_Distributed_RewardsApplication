package tourGuide.service;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import preferencesModule.domain.UserPreferences;
import rewardModule.domain.UserReward;
import tourGuide.domain.NearbyAttraction;
import tourGuide.domain.User;
import tripPricer.Provider;

import java.util.HashMap;
import java.util.List;

public interface ITourGuideService {


    User getUser(String userName);

    List<User> getAllUsers();

    void addUser(User user);

    List<UserReward> getUserRewards(User user);

    VisitedLocation getUserLocation(User user);

    HashMap<String, Location> getAllCurrentLocations();

    List<Provider> getTripDeals(User user);

    VisitedLocation trackUserLocation(User user);

    List<NearbyAttraction> getNearByAttractions(VisitedLocation visitedLocation, User user);

    UserPreferences getUserPreferences(User user);

    UserPreferences postUserPreferences(User user, UserPreferences userPreferences);

    void addShutDownHook();
}
