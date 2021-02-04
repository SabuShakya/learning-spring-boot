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
@Profile("dev")
@ConfigurationProperties("mail")
@PropertySource(factory = YamlPropertySourceFactory.class,value = "classpath:application-${spring.profiles.active}.yml")
public class DevConfigs {
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
