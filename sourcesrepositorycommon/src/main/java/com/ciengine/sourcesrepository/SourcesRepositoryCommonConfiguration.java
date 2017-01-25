package com.ciengine.sourcesrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;


@ComponentScan({ "com.ciengine.sourcesrepository" })
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class SourcesRepositoryCommonConfiguration
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
