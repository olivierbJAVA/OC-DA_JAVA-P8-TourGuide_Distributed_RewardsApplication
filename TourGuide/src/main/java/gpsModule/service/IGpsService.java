package gpsModule.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

import java.util.List;
import java.util.UUID;

public interface IGpsService {

    VisitedLocation getUserLocation(UUID userId);

    List<Attraction> getAttractions();
}
