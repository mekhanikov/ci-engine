package com.ciengine.webapp.web;

import com.ciengine.CommonConfiguration;
import com.ciengine.sourcesrepository.SourcesRepositoryCommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({CommonConfiguration.class, SourcesRepositoryCommonConfiguration.class})
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
