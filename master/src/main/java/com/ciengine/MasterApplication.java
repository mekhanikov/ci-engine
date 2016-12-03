package com.ciengine;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.master.ArtefactoryBinaryRepositoryClient;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.facades.CIAgentFacadeImpl;
import com.ciengine.master.listeners.OnCommit;
import com.ciengine.master.listeners.OnNewArtefact;
import com.ciengine.master.listeners.OnReleaseSubmited;
import com.ciengine.master.listeners.RuleBuilder;
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
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.util.Properties;


@ComponentScan({ "com.ciengine.master" })
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
@EnableSwagger2
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class MasterApplication
{
	@Autowired
	private Environment environment;

//	@Autowired
//	BuildRunner buildRunner;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MasterApplication.class, args);
//		Map<String, CIEngineListener> stringCIEngineListenerMap = ctx.getBeansOfType(CIEngineListener.class);
//		System.out.println(stringCIEngineListenerMap);
		//Application application = ctx.getBean(Application.class);
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto",environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.ciengine.master.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public CIAgentFacade ciAgentFacade() {
		CIAgentFacade ciAgentFacade = new CIAgentFacadeImpl();
		return ciAgentFacade;
	}

//	@Bean
//	public CIEngineFacade ciAgentFacade() {
//		CIEngineFacade ciEngineFacade = new CIEngineFacadeImpl();
//		return ciEngineFacade;
//	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}

	@Bean
	@Scope("prototype")
	public RuleBuilder thing(CIEngineEvent ciEngineEvent) {
		return new RuleBuilder(ciEngineEvent);
	}

	@Bean
	@Scope("prototype")
	public OnNewArtefact onNewArtefact(CIEngineEvent ciEngineEvent) {
		return new OnNewArtefact(ciEngineEvent);
	}

	@Bean
	@Scope("prototype")
	public OnReleaseSubmited onReleaseSubmited(CIEngineEvent ciEngineEvent) {
		return new OnReleaseSubmited(ciEngineEvent);
	}

	@Bean
	@Scope("prototype")
	public OnCommit onCommit(CIEngineEvent ciEngineEvent) {
		return new OnCommit(ciEngineEvent);
	}


//	@Bean
//	public BuildDao customerDao() {
//		return  new BuildDao();
//	}
@Bean
//@Primary
public ArtefactoryBinaryRepositoryClient binaryRepositoryClient() {
	return new ArtefactoryBinaryRepositoryClient();
}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("greetings")
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
