# @ConfigurationProperties #
Let us consider following entries in a property file(say 
application.properties)
```properties
user.firstName = Umesh
user.lastName = Awasthi
user.greeting = Hello Umesh
user.blogName = umeshawasthi.com
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
username=umesh
welcomeMessage = Welcome Umesh!!!
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