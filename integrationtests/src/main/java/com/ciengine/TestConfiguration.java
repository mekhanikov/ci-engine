package com.ciengine;

import com.ciengine.agent.steps.impl.MockMavenStep;
import com.ciengine.common.BinaryRepositoryClient;
import com.ciengine.common.CIEngineStep;
import com.ciengine.master.MockBinaryRepositoryClient;
import com.ciengine.master.MockCIAgentFacadeImpl;
import com.ciengine.master.facades.CIAgentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * Created by emekhanikov on 26.09.2016.
 */
//@Profile("test")
//@Configuration
@EnableAsync
@Import({CommonConfiguration.class, MasterApplication.class, AgentApplication.class})//
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)

//@ComponentScan({ "com.ciengine" })
//@EnableScheduling
//@EnableTransactionManagement
//@PropertySource(value = { "classpath:application.properties" })
//@EnableSwagger2


//@ComponentScan({ "com.ciengine.master" })
//@EnableScheduling
////@SpringBootApplication
////@EnableTransactionManagement
//@PropertySource(value = { "classpath:application.properties" })
////@EnableSwagger2
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class TestConfiguration extends AsyncConfigurerSupport
{

	@Autowired
	private Environment environment;

	@Bean
	@Primary
	public CIAgentFacade ciAgentFacade() {
		return new MockCIAgentFacadeImpl();
	}

	@Bean
	@Primary
	public BinaryRepositoryClient binaryRepositoryClient() {
		return new MockBinaryRepositoryClient();
	}

	@Bean("mavenStep")
	@Primary
	public CIEngineStep mavenStep() {
		return new MockMavenStep();
	}

//	@Bean
////	@Primary
//	public OnNewArtifactEventListener onNewArtifactEventListener() {
//		return new OnNewArtifactEventListener();
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
//	public static void main(String[] args) {
//		ApplicationContext ctx = SpringApplication.run(TestConfiguration.class, args);
////		Map<String, CIEngineListener> stringCIEngineListenerMap = ctx.getBeansOfType(CIEngineListener.class);
////		System.out.println(stringCIEngineListenerMap);
//		//CommonConfiguration application = ctx.getBean(CommonConfiguration.class);
//	}
//
//
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
//		return dataSource;
//	}
//
//	private Properties hibernateProperties() {
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
//		properties.put("hibernate.hbm2ddl.auto",environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
//		return properties;
//	}
//
//	@Bean
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource());
//		sessionFactory.setPackagesToScan(new String[] { "com.ciengine.master.model" });
//		sessionFactory.setHibernateProperties(hibernateProperties());
//		return sessionFactory;
//	}
////
////	@Bean
////	public CIAgentFacade ciAgentFacade() {
////		CIAgentFacade ciAgentFacade = new CIAgentFacadeImpl();
////		return ciAgentFacade;
////	}
//
////	@Bean
////	public CIEngineFacade ciAgentFacade() {
////		CIEngineFacade ciEngineFacade = new CIEngineFacadeImpl();
////		return ciEngineFacade;
////	}
//
//	@Bean
//	@Autowired
//	public HibernateTransactionManager transactionManager(SessionFactory s) {
//		HibernateTransactionManager txManager = new HibernateTransactionManager();
//		txManager.setSessionFactory(s);
//		return txManager;
//	}
//
////	@Bean
////	public BuildDao customerDao() {
////		return  new BuildDao();
////	}
//
////
////	@Bean
////	public Docket newsApi() {
////		return new Docket(DocumentationType.SWAGGER_2)
////				.groupName("greetings")
////				.apiInfo(apiInfo())
////				.select()
//////				.paths(regex("/greeting.*"))
////				.build();
////	}
////
////	private ApiInfo apiInfo() {
////		return new ApiInfoBuilder()
////				.title("Spring REST Sample with Swagger")
////				.description("Spring REST Sample with Swagger")
////				.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
////				.contact("Niklas Heidloff")
////				.license("Apache License Version 2.0")
////				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
////				.version("2.0")
////				.build();
////	}
}
