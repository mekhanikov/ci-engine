package com.ciengine.master;

import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.model.BuildModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({ "com.ciengine" })
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class Application
{


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

	private void run()
	{
		buildRunner.run();
	}


	@Bean
	public DataSource dataSource() {

		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
//				.addScript("db/sql/create-db.sql")
//				.addScript("db/sql/insert-data.sql")
				.build();
		return db;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory createSessionFactory() throws ClassNotFoundException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(BuildModel.class);

		Properties properties = new Properties();
		properties.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto","update");

		configuration.setProperties(properties);


		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		standardServiceRegistryBuilder.applySettings(configuration.getProperties());
		standardServiceRegistryBuilder.applySetting(Environment.DATASOURCE,dataSource());

		MetadataSources metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());

		metadataSources.addPackage("com.ciengine");
		return metadataSources.getMetadataBuilder().build().buildSessionFactory();

		//return metadataSources.getMetadataBuilder().build().getSessionFactoryBuilder().build();


	}

	@Bean
	public BuildDao customerDao() {
		return  new BuildDao();
	}

}
