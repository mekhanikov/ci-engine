package com.ciengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;


@ComponentScan({ "com.ciengine.common" })
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class CommonConfiguration
{
	@Autowired
	private Environment environment;

//	@Autowired
//	BuildRunner buildRunner;
//
//	public static void main(String[] args) {
//		ApplicationContext ctx = SpringApplication.run(CommonConfiguration.class, args);
////		Map<String, CIEngineListener> stringCIEngineListenerMap = ctx.getBeansOfType(CIEngineListener.class);
////		System.out.println(stringCIEngineListenerMap);
//		//CommonConfiguration application = ctx.getBean(CommonConfiguration.class);
//	}

}
