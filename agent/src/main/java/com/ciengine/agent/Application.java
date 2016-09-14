package com.ciengine.agent;

import com.ciengine.agent.lists.ListExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableAutoConfiguration
@PropertySource(value = { "classpath:application.properties" })
public class Application {

	@Autowired
	ListExecutor listExecutor;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		Application application = ctx.getBean(Application.class);
		application.run();
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
		listExecutor.run();
	}

}
