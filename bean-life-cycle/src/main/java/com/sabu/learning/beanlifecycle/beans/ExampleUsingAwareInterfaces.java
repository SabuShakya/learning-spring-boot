//package com.sabu.learning.beanlifecycle.beans;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanClassLoaderAware;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.BeanNameAware;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ExampleUsingAwareInterfaces implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware {
//
//    @Override
//    public void setBeanName(String s) {
//        System.out.println("Spring Set Bean Name Method Call");
//    }
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        System.out.println("Spring Set Bean Factory Method Call");
//    }
//
//    @Override
//    public void setBeanClassLoader(ClassLoader classLoader) {
//        System.out.println("Spring Set Bean Class Loader Method Call");
//    }
//}
