package com.ciengine.sourcesrepository;

import com.ciengine.sourcesrepository.impl.SourcesRepositoryFacadeImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan({ "com.ciengine.sourcesrepository" })
@EnableScheduling
@SpringBootApplication
//@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
@EnableSwagger2
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class SourcesRepositoryApplication
{
	@Autowired
	private Environment environment;

//	@Autowired
//	BuildRunner buildRunner;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SourcesRepositoryApplication.class, args);
//		Map<String, CIEngineListener> stringCIEngineListenerMap = ctx.getBeansOfType(CIEngineListener.class);
//		System.out.println(stringCIEngineListenerMap);
		//Application application = ctx.getBean(Application.class);
	}
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

//	private Properties hibernateProperties() {
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
//		properties.put("hibernate.hbm2ddl.auto",environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
//		return properties;
//	}

//	@Bean
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource());
//		sessionFactory.setPackagesToScan(new String[] { "com.ciengine.master.model" });
//		sessionFactory.setHibernateProperties(hibernateProperties());
//		return sessionFactory;
//	}

	@Bean
	public SourcesRepositoryFacade sourcesRepositoryFacade() {
		SourcesRepositoryFacade sourcesRepositoryFacade = new SourcesRepositoryFacadeImpl();
		return sourcesRepositoryFacade;
	}

//	@Bean
//	public CIEngineFacade ciAgentFacade() {
//		CIEngineFacade ciEngineFacade = new CIEngineFacadeImpl();
//		return ciEngineFacade;
//	}

//	@Bean
//	@Autowired
//	public HibernateTransactionManager transactionManager(SessionFactory s) {
//		HibernateTransactionManager txManager = new HibernateTransactionManager();
//		txManager.setSessionFactory(s);
//		return txManager;
//	}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				//.groupName("greetings")
				.apiInfo(apiInfo())
				.select()
//				.paths(regex("/greeting.*"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring REST Sample with Swagger")
				.description("Spring REST Sample with Swagger")
				.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
				.contact("Niklas Heidloff")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
				.version("2.0")
				.build();
	}
}
