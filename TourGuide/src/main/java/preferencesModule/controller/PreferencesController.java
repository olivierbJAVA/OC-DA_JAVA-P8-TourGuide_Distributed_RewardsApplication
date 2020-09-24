package preferencesModule.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import preferencesModule.service.IPreferencesService;

import java.util.UUID;

@RestController
public class PreferencesController {

    @Autowired
    IPreferencesService preferencesService;

    @RequestMapping("/getPrice")
    public String getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) {
        return JsonStream.serialize(preferencesService.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints));
    }
}
