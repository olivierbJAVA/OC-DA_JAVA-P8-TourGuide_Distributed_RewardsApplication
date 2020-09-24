package gpsModule.configuration;

import gpsModule.service.GpsServiceImpl;
import gpsModule.service.IGpsService;
import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpsConfiguration {
	
	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

	@Bean
	public IGpsService getGpsService() {
		return new GpsServiceImpl(getGpsUtil());
	}
}
