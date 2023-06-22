package com.sabu.learning.beanlifecycle.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class HookExampleUsingInterfaces implements BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {

    public void hookExampleUsingInterfaces() {
        System.out.println("Bean Ready : Hello from HookExampleUsingInterfaces.");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("Populating Properties :--- setBeanName executed ---");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Populating Properties : --- setApplicationContext executed ---");
    }

    @PostConstruct
    public void postConstructMethod() {
        System.out.println("Pre-Initialization:: Hi I am post construct using @PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AfterPropertiesSet::Spring Bean post method after properties set.");
    }

    public void onInit() {
        System.out.println("Custom Initialization:: Spring  @Bean Initialization Method Call");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Pre Destroy::I am pre destroy using @preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Destroy:Spring is being destroyed.");
    }

    public void onDestroy() {
        System.out.println("CustomDestruction : Spring @Bean Destroy Method");
    }
}
