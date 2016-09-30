package com.ciengine.master;

import com.ciengine.master.facades.CIAgentFacade;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.Executor;


/**
 * Created by emekhanikov on 26.09.2016.
 */
//@Profile("test")
@Configuration
//@EnableAsync
//@Import(Application.class)
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)

//@ComponentScan({ "com.ciengine" })
//@EnableScheduling
//@EnableTransactionManagement
//@PropertySource(value = { "classpath:application.properties" })
//@EnableSwagger2
public class TestConfiguration// extends AsyncConfigurerSupport
{
	@Bean
	@Primary
	public CIAgentFacade ciAgentFacade() {
		return new MockCIAgentFacadeImpl();
	}

//	@Override
//	public Executor getAsyncExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(2);
//		executor.setMaxPoolSize(2);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("GithubLookup-");
//		executor.initialize();
//		return executor;
//	}
}
