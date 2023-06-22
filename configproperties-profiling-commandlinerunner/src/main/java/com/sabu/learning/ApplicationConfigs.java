package com.sabu.learning;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@Profile("prod")
@ConfigurationProperties("mail")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:application-${spring.profiles.active}.yml")
//@PropertySource("classpath:custom.properties") // if using .properties just add file name
public class ApplicationConfigs {
    private String from;
    private String host;
    private int port;
    private Security security;

    @Getter
    @Setter
    public static class Security {
        private String userName;
        private String password;
    }
}
