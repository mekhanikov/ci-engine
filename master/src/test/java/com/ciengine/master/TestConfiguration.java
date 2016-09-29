package com.ciengine.master;

import com.ciengine.master.facades.CIAgentFacade;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * Created by emekhanikov on 26.09.2016.
 */
@Profile("test")
@Configuration
@EnableAsync
@ImportAutoConfiguration(Application.class)
@SpringBootApplication
public class TestConfiguration extends AsyncConfigurerSupport
{
	@Bean
	@Primary
	public CIAgentFacade ciAgentFacade() {
		return new MockCIAgentFacadeImpl();
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}
}
