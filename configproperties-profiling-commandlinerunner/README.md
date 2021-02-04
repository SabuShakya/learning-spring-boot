# @ConfigurationProperties #
Let us consider following entries in a property file(say 
application.properties)
```properties
user.firstName = sabu
user.lastName = shakys
user.greeting = Hello Sabu
user.blogName = sabushakya.com
```

To use these properties in our application, we have to use it using @Value:

```java
public class SimpleSpringPropertyTest {
    @Value("${user.firstName}") 
    private String firstName;

    @Value("${user.lastName}") 
    private String lastName;
} 
```

@Value(“${property}”) is handy and easy but becomes tedious when we have to read several properties.
So instead we use *@ConfigurationProperties*. We create a simple POJO class to store our configuration properties by
annotating it with `@Configuration` and `@ConfigurationProperties`.

Let's assume we have given properties file.
```properties
#Database Configuration

db.driver =org.hsqldb.jdbcDriver
db.username	=test
db.password	=test
db.tablePrefix =_prefix

#SMTP Configuration

mail.from =test@test.com	 
mail.host =test@test.com
mail.port =25
mail.security.userName 	=test
mail.security.password 	=test

#Server Configurations

server.tomcat.httpPort =80
server.tomcat.sslPort =443
server.tomcat.ajpPort =444
server.tomcat.jmxPort =445

#Global Properties
username=sabu
welcomeMessage = Welcome Sabu!!!
```

To access all the properties with prefix “mail”:
```java
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail")
@PropertySource("classpath:custom.properties")
public class ApplicationConfigurationProp {

    private String from;
    private String host;
    private int port;
    private Security security;

    @Getter
    @Setter
    public static class Security{
        private String userName;
        private String password;
    }
}
```
If we have defined the properties in some other custom.properties file, we have to add
*`@PropertySource(“classpath:custom.properties”)`*

```java
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail")
@PropertySource("classpath:custom.properties")
public class ApplicationConfigurationProp {
    // ....
}
```

## In case of YAML files ##
@PropertySource doesn't load YAML files. But as of Spring 4.3, it comes with the factory attribute which can be used to 
provide custom implementation of the *PropertySourceFactory*(Strategy interface for creating resource-based 
PropertySource wrappers.), which will handle YAML file processing.

```java
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) 
      throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = factory.getObject();

        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}
```
In our custom implementation, first, we used the YamlPropertiesFactoryBean to convert the resources in YAML format to 
the java.util.Properties object.Then, we simply returned a new instance of the PropertiesPropertySource, 
which is a wrapper that allows Spring to read the parsed properties.

```java
@Getter
@Setter
@Configuration
@ConfigurationProperties("mail")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:custom-config.yml")
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
```

And let's test,

```java
@SpringBootApplication
public class ConfigpropertiesProfilingCommandlinerunnerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigpropertiesProfilingCommandlinerunnerApplication.class, args);
        ApplicationConfigs applicationConfigs = context.getBean(ApplicationConfigs.class);
        System.out.println("Application Configs From====>"+applicationConfigs.getFrom());
        System.out.println("Application Configs HOST====>"+applicationConfigs.getHost());
        System.out.println("Application Configs USERNAME====>"+applicationConfigs.getSecurity().getUserName());
        System.out.println("Application Configs PASSWORD====>"+applicationConfigs.getSecurity().getPassword());
    }
}
```

Reference:
- https://www.baeldung.com/spring-yaml-propertysource
- https://www.javadevjournal.com/spring-boot/configuration-properties-in-spring-boot/


# Profiles #

Profiles are a great tool for providing configuration properties for different environments like local development,
test, staging and production environment.

## @Profile Annotation ##
 We can specify the environment by using the `@Profile` annotation as 
 
```java
@Configuration
public class DefaultConfigurations {
    // Skipped Configurations
}
@Configuration
@Profile("dev")
public class DevConfigurations {
    // Skipped Configurations
}
@Configuration
@Profile("prod")
public class ProdConfigurations {
    // Skipped Configurations
}
```
 
 Spring profiles can also be used on a Bean, to map that bean to a particular profile.
 
```java
@Component
@Profile("dev")
public class DevDatasourceConfig{}

@Component
@Profile("!dev")// activate only if dev profile is not active
public class DevDatasourceConfig{}
```

## SET PROFILES ##
   - [Using Property File](#using-property-file)
   - [Programmatically via WebApplicationInitializer Interface](#programmatically-via-webapplicationinitializer-interface)
   - [Programmatically via ConfigurableEnvironment](#programmatically-via-configurableenvironment)
   - [Maven Profile](#maven-profile)
    
### Using Property File ###

```properties
spring.profiles.active=development,staging
```
or,
```yaml
spring:
  profiles:
    active: dev
```

### Programmatically via WebApplicationInitializer Interface ###

```java
@Configuration
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "dev");
    }
}
```

### Programmatically via ConfigurableEnvironment ###

```java
@Autowired
private ConfigurableEnvironment env;
...
env.setActiveProfiles("someProfile");
```

### Maven Profile ###
```xml
<profiles>
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <spring.profiles.active>dev</spring.profiles.active>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <spring.profiles.active>prod</spring.profiles.active>
        </properties>
    </profile>
</profiles>
```
Its value will be used to replace the *@spring.profiles.active@* placeholder in application.properties:

```properties
spring.profiles.active=@spring.profiles.active@
```
Next we need to enable resource filtering in pom.xml:
```xml
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
    
</build>
```
and append a -P parameter to switch which Maven profile will be applied:
```
mvn clean package -Pprod
```

## GET ACTIVE PROFILES ##

By using environment:

```java
public class ProfileManager {
    @Autowired
    private Environment environment;

    public void getActiveProfiles() {
        System.out.println("active profile - " + environment.getProperty("spring.profiles.active"));
        for (String profileName : environment.getActiveProfiles()) {
            System.out.println("Currently active profile - " + profileName);
        }  
    }
}
```
By injecting property spring.profiles.active

```java
@Value("${spring.profiles.active}")
private String activeProfile;
```