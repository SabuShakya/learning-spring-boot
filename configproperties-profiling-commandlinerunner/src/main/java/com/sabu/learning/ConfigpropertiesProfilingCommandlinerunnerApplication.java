package com.sabu.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class ConfigpropertiesProfilingCommandlinerunnerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigpropertiesProfilingCommandlinerunnerApplication.class, args);
        if (Objects.equals(context.getEnvironment().getProperty("spring.profiles.active"), "prod")) {
            ApplicationConfigs applicationConfigs = context.getBean(ApplicationConfigs.class);
            System.out.println("Application Configs From====>" + applicationConfigs.getFrom());
            System.out.println("Application Configs HOST====>" + applicationConfigs.getHost());
            System.out.println("Application Configs USERNAME====>" + applicationConfigs.getSecurity().getUserName());
            System.out.println("Application Configs PASSWORD====>" + applicationConfigs.getSecurity().getPassword());
        } else if (Objects.equals(context.getEnvironment().getProperty("spring.profiles.active"), "dev")) {
            DevConfigs applicationConfigs = context.getBean(DevConfigs.class);
            System.out.println("Dev Configs From====>" + applicationConfigs.getFrom());
            System.out.println("Dev Configs HOST====>" + applicationConfigs.getHost());
            System.out.println("Dev Configs USERNAME====>" + applicationConfigs.getSecurity().getUserName());
            System.out.println("Dev Configs PASSWORD====>" + applicationConfigs.getSecurity().getPassword());
        }
    }

}
