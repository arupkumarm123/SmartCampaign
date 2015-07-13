package org.akm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Hello world!
 *
 */

@EnableAutoConfiguration
@EnableMongoRepositories
@SpringBootApplication
@Configuration
@EnableWebMvc
@EnableIntegration
@IntegrationComponentScan
@EnableAsync
public class App  
{
	

    public static void main( String[] args ) {
       SpringApplication.run(App.class, args);
    }
}
