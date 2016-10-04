package com.ciengine;

import com.ciengine.agent.lists.ListExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@ComponentScan({ "com.ciengine.agent" })
//@SpringBootApplication
//@EnableAutoConfiguration
@PropertySource(value = { "classpath:application.properties" })
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)

public class AgentApplication {

	@Autowired
	ListExecutor listExecutor;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AgentApplication.class, args);
		AgentApplication agentApplication = ctx.getBean(AgentApplication.class);
		agentApplication.run();
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
