package preferencesModule.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import preferencesModule.service.IPreferencesService;
import preferencesModule.service.PreferencesServiceImpl;
import tripPricer.TripPricer;

@Configuration
public class PreferencesConfiguration {
	
	@Bean
	public TripPricer getTripPricer() {
		return new TripPricer();
	}

	@Bean
	public IPreferencesService getPreferencesService() {
		return new PreferencesServiceImpl(getTripPricer());
	}
}
