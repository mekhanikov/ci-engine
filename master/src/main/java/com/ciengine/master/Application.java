package com.ciengine.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application
{

	@Autowired
	BuildRunner buildRunner;

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
		buildRunner.run();
	}

}
