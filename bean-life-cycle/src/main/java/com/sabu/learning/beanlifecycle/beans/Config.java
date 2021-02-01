package com.sabu.learning.beanlifecycle.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.sabu.learning.beanlifecycle.beans")
public class Config {

//    @Bean(initMethod = "init", destroyMethod = "destroy")
//    public LifecycleHookUsingBeanAttributes lifecycleHookUsingBeanAttributes(){
//        return new LifecycleHookUsingBeanAttributes();
//    }

    @Bean
    public ExampleUsingBeanPostProcessor exampleUsingBeanPostProcessor(){
        return new ExampleUsingBeanPostProcessor();
    }
    @Bean(initMethod = "onInit",destroyMethod = "onDestroy")
    public HookExampleUsingInterfaces hookExampleUsingInterfaces(){
        return new HookExampleUsingInterfaces();
    }
}
