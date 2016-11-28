package com.ciengine.webapp.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("net/thumbtack/omarov/trackingframework")
@PropertySources({@PropertySource(value = "classpath:application.properties",
        ignoreResourceNotFound = true)})
public class AppConfiguration {
    @Value("${redis.ip}")
    private String redisIp;
    @Value("${redis.port}")
    private int redisPort;

    @Autowired
    private Environment environment;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisIp);
        jedisConnectionFactory.setPort(redisPort);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Long> redisTemplate() {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
