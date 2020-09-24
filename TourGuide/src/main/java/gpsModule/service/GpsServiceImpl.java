package gpsModule.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GpsServiceImpl implements IGpsService {

    private final GpsUtil gpsUtil;

    public GpsServiceImpl(GpsUtil gpsUtil) {
        this.gpsUtil = gpsUtil;
    }

    @Override
    public VisitedLocation getUserLocation(UUID userId){
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);
        return visitedLocation;
    }

    @Override
    public List<Attraction> getAttractions() {
        List<Attraction> allAttractions = gpsUtil.getAttractions();
        return allAttractions;
    }
}
