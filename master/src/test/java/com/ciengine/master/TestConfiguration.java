package com.ciengine.master;

import com.ciengine.master.facades.CIAgentFacade;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;


/**
 * Created by emekhanikov on 26.09.2016.
 */
@Profile("test")
@Configuration
@ImportAutoConfiguration(Application.class)
public class TestConfiguration
{
	@Bean
	@Primary
	public CIAgentFacade ciAgentFacade() {
		return Mockito.mock(CIAgentFacade.class);
	}
}
