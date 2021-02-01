# Bean Lifecycle #

![Alt text](./beanlifecycle.png?raw=true "Title")

## Bean Creation Phase ##
|||
|---|:---|
|Instantiation| Starting point. Spring will instantiate bean objects.|
|Populating Properties | After instantiation, spring scans the beans that implement 'Aware' interfaces and start setting relevant properties. |
|Pre-Initialization | Spring's BeanPostProcessor get into action and postProcessBeforeInitialization() methods do their job. Also @PostConstruct annotated methods run after them.|
|AfterPropertiesSet | Spring Executes afterPropertiesSet() methods of beans implementing InitializationBean.|
|Custom Initialization |Spring triggers initialization methods we defined in the initMethod attribute of our @Bean annotations |
|Post-Initialization|Spring's BeanPostProcessor are in action for second time and triggers postProcessAfterInitialization() methods.|

## Bean Destruction Phase ##

|||
|---|:---|
|Pre-Destroy | Spring triggers @PreDestroy annotated methods.|
|Destroy | triggers destroy() methods of DisposableBean implementations.|
|Custom Destruction| triggers custom destruction methods provided in destroyMethod attribute in @Bean annotation.|

## Hooking Into the Bean Lifecycle ##

*Why do we need to Hook into bean lifecycle?*
Hooking into bean lifecycle is a good way to extend the application in most cases. Other benefits:
- [Acquiring Bean Properties (like bean name at runtime)](https://reflectoring.io/spring-bean-lifecycle/#acquiring-bean-properties)
- [Dynamically Changing spring Bean Instances](https://reflectoring.io/spring-bean-lifecycle/#dynamically-changing-spring-bean-instances)
- [Accessing Beans From the Outside of the Spring Context](https://reflectoring.io/spring-bean-lifecycle/#accessing-beans-from-the-outside-of-the-spring-context)

Different Ways to Hook into Bean Lifecycle:
- [Using Spring Interfaces](#using-spring-interfaces)
- [Using JSR-250 Annotations](#using-jsr-250-annotations)
- [Using Attributes of @Bean annotation](#using-attributes-of-bean-annotation)
- [Using BeanPostProcessor](#using-beanpostprocessor)
- [Using Aware Interfaces](#using-aware-interfaces)

Best Practice, according to Spring Document preference is to use JSR-250 annotations(@PostConstruct, @PreDestroy) and then
@Bean init-method and destroy-method options.

### Using Spring interfaces ###
We can implement InitializationBean interface to run custom operations in afterPropertiesSet() phase. Similarly,
we can implement DisposableBean to call destroy method in destroy phase.

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class HookExampleUsingInterfaces implements InitializingBean, DisposableBean {

    public void hookExampleUsingInterfaces() {
        System.out.println("Hello from HookExampleUsingInterfaces.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Spring is being destroyed.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Spring Bean post method after properties set.");
    }
}
```

### Using JSR-250 Annotations ###

Using `@PostContruct` and `@PreDestroy` annotations.

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifecycleHookUsingAnnotations {

    @PostConstruct
    public void  postConstructMethod(){
        System.out.println("Hi I am post construct using @PostConstruct");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("I am pre destroy using @preDestroy");
    }
}
```

### Using Attributes of @Bean annotation ###

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.stereotype.Component;

@Component
public class LifecycleHookUsingBeanAttributes {

    public void init() {
        System.out.println("Spring @Bean Initialization Method Call");
    }

    public void destroy() {
        System.out.println("Spring @Bean Destroy Method");
    }
}
```

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.sabu.learning.beanlifecycle.beans")
public class Config {
    
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public LifecycleHookUsingBeanAttributes lifecycleHookUsingBeanAttributes(){
        return new LifecycleHookUsingBeanAttributes();
    }
}
```

### Using BeanPostProcessor ###

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExampleUsingBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("In postProcessBeforeInitialization.");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("In postProcessAfterInitialization.");
        return bean;
    }
}
```

### Using Aware Interfaces ###

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component
public class ExampleUsingAwareInterfaces implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware {

    @Override
    public void setBeanName(String s) {
        System.out.println("Spring Set Bean Name Method Call");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Spring Set Bean Factory Method Call");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Spring Set Bean Class Loader Method Call");
    }
}
```

### The Execution Order ###
Let's add all the lifecycle hooks to our class HookExampleUsingInterfaces, and add a BeanPostProcessor implementation:

```java
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
```

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ExampleUsingBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("In postProcessBeforeInitialization.");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("In postProcessAfterInitialization.");
        return bean;
    }
}
```
Let's create a bean of HookExampleUsingInterfaces in our config class.

```java
package com.sabu.learning.beanlifecycle.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.sabu.learning.beanlifecycle.beans")
public class Config {

    @Bean
    public ExampleUsingBeanPostProcessor exampleUsingBeanPostProcessor(){
        return new ExampleUsingBeanPostProcessor();
    }

    @Bean(initMethod = "onInit",destroyMethod = "onDestroy")
    public HookExampleUsingInterfaces hookExampleUsingInterfaces(){
        return new HookExampleUsingInterfaces();
    }
}

```
And to our main method:
```java
package com.sabu.learning.beanlifecycle;

import com.sabu.learning.beanlifecycle.beans.Config;
import com.sabu.learning.beanlifecycle.beans.HookExampleUsingInterfaces;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BeanLifeCycleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeanLifeCycleApplication.class, args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        HookExampleUsingInterfaces hookExampleUsingInterfaces = context.getBean(HookExampleUsingInterfaces.class);
        hookExampleUsingInterfaces.hookExampleUsingInterfaces();
        context.close();
    }
}
```
When we run our application, we see the following output:
![Alt text](./lifecycleresult.jpg?raw=true "Output")

All the methods are called exactly in the same order as depicted in the lifecycle diagram.

References:
- [Hooking Into the Spring Bean Lifecycle](https://reflectoring.io/spring-bean-lifecycle/)
- [The Lifecycle of Spring Beans](https://medium.com/swlh/the-lifecycle-of-spring-beans-b0edb8936189)