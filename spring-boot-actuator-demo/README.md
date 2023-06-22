# SPRING BOOT ACTUATOR #

Actuator is a tool which helps to monitor and manage application in production. It provides several features like health
check-up, auditing JVM metrics, log information, caching statics, etc.

It exposes endpoints prefixed with */actuator*

We need to add the following dependency:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Default exposed end points:

| End points | Description |
| --- | :--- |
|/health| shows status of our application|
|/info| displays general information from the standard files like META-INF/build-info.properties. |

## Enable/Disable Actuator Endpoints ##

To enable an endpoint, use the management.endpoint.< id >.enabled property in the application.properties file.

```
management.endpoints.enabled-by-default=false
management.endpoint.info.enabled=true
```

To include / exclude the endpoint over HTTP, we should configure the following properties through the
application.properties:

```
 management.endpoints.web.exposure.include=health,info 
 management.endpoints.web.exposure.exclude=
```

or

```
#we can access All our actuator endpoints without requiring authentication
management.endpoints.web.exposure.include=*
```
                                                                                                     


