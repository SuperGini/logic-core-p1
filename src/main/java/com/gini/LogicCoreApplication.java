package com.gini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
@ConfigurationPropertiesScan
public class LogicCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogicCoreApplication.class, args);
    }

}
