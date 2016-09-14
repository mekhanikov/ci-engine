package com.ciengine.master;

import com.ciengine.master.dao.BuildDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({ "com.ciengine" })
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class Application
{

	@Autowired
	private Environment environment;

	@Autowired
	BuildRunner buildRunner;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		Application application = ctx.getBean(Application.class);
		//application.run();
//
//		System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
	}
//
//	private void run()
//	{
//		buildRunner.run();
//	}

//
//	@Bean(name = "sessionFactory")
//	public SessionFactory createSessionFactory() throws ClassNotFoundException {
//		Configuration configuration = new Configuration();
//		//configuration.addAnnotatedClass(BuildModel.class);
//
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
//		properties.put("hibernate.show_sql", "true");
//		properties.put("hibernate.hbm2ddl.auto","update");
//
//		configuration.setProperties(properties);
//
//
//		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
//		standardServiceRegistryBuilder.applySettings(configuration.getProperties());
//		standardServiceRegistryBuilder.applySetting(Environment.DATASOURCE,dataSource());
//
//		MetadataSources metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
//
//		//		metadataSources.addPackage("com.ciengine.master.model");
//		metadataSources.addAnnotatedClass(BuildModel.class);
//		return metadataSources.getMetadataBuilder().build().buildSessionFactory();
//
//		//return metadataSources.getMetadataBuilder().build().getSessionFactoryBuilder().build();
//
//
//	}

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
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}

	@Bean
	public BuildDao customerDao() {
		return  new BuildDao();
	}

}
