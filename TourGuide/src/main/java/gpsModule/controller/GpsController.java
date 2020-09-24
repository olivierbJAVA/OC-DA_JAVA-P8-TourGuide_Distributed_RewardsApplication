package gpsModule.controller;

import com.jsoniter.output.JsonStream;
import gpsModule.service.IGpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GpsController {

    @Autowired
    IGpsService gpsService;

    @RequestMapping("/getUserLocation")
    public String getUserLocation(UUID userId){
        VisitedLocation visitedLocation = gpsService.getUserLocation(userId);
        return JsonStream.serialize(visitedLocation);
    }

    @RequestMapping("/getAttractions")
    public String getAttractions() {
        List<Attraction> allAttractions = gpsService.getAttractions();
        return JsonStream.serialize(allAttractions);
    }
}
