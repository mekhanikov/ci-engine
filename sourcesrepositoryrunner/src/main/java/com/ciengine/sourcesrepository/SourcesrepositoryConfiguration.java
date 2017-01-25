package com.ciengine.sourcesrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * Created by emekhanikov on 26.09.2016.
 */
@EnableAsync
@Import({SourcesRepositoryCommonConfiguration.class, SourcesRepositoryApplication.class})//
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class SourcesrepositoryConfiguration extends AsyncConfigurerSupport
{

	@Autowired
	private Environment environment;
//
//	@Bean
//	@Primary
//	public CIAgentFacade ciAgentFacade() {
//		return new MockCIAgentFacadeImpl();
//	}
//
//	@Bean
//	@Primary
//	public BinaryRepositoryClient binaryRepositoryClient() {
//		return new MockBinaryRepositoryClient();
//	}
//
//	@Bean("mavenStep")
//	@Primary
//	public CIEngineStep mavenStep() {
//		return new MockMavenStep();
//	}

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
//
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SourcesrepositoryConfiguration.class, args);
//		Map<String, CIEngineListener> stringCIEngineListenerMap = ctx.getBeansOfType(CIEngineListener.class);
//		System.out.println(stringCIEngineListenerMap);
		//CommonConfiguration application = ctx.getBean(CommonConfiguration.class);
	}

}
