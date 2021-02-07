# Logging in Spring Boot #

For every project, applications being developed we should keep track of the issues. We need to track what functions have 
been loaded and what parameters have been passed and many more(need to know what is going on in the application). 
This particular tracked content will be used as  future reference. Let's think of cases where the customer say they 
haven't received the email from system, or certain data hasn't been persisted in the database, or some other issues occurred.
In such scenarios, the tracked content will help us in identifying the issues. Keeping track of such issues and information
is logging. We keep logs of messages and errors using certain objects either in a file or in a console.

- [Logback vs SLF4J vs Log4J2 - what is the difference? Java Brains Brain Bytes](https://youtu.be/SWHYrCXIL38)

When we add spring-boot-starter-web dependency, it automatically pulls in spring-boot-starter-logging dependency as well.
spring-boot-starter-logging dependency then pulls in spring-jcl(spring commons logging bridge) dependency. 
These two dependencies provide with logging functionalities. By default Spring boot uses LogBack which can be changed.
 
## Simple Logging Facade for Java (slf4j) ##
It serves as a simple facade or abstraction for various logging frameworks(e.g. java.util.logging, logback, log4j)
allowing the end user to plug in the desired logging framework at deployment time. It is simply just an API.

Let's implement a simple logger:

```java
package com.sabu.learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/")
    public String sayHello() {
        logger.error("Error Occurred!");
        logger.debug("This is a debug message!");
        logger.warn("This is a warning message!");
        logger.info("This is a info message!");
        return "Hello,Let's learn Logger!";
    }
}
```
When we run the application, we see the following output:

```
2021-02-05 13:14:31.515 ERROR 8441 --- [nio-8080-exec-1] com.sabu.learning.LogController          : Error Occurred!
2021-02-05 13:14:31.515  WARN 8441 --- [nio-8080-exec-1] com.sabu.learning.LogController          : This is a warning message!
2021-02-05 13:14:31.515  INFO 8441 --- [nio-8080-exec-1] com.sabu.learning.LogController          : This is a info message!
```
The above output follows the following pattern:
- Date and Time: Millisecond precision and easily sortable.
- Log Level: ERROR, WARN, INFO, DEBUG, or TRACE.
- Process ID.
- A --- separator to distinguish the start of actual log messages.
- Thread name: Enclosed in square brackets (maybe truncated for console output).
- Logger name: This is commonly the source class name (often abbreviated).
- The log message.

## Log Levels ##

Log levels are just like labels we use while storing documents. They're simply a means of categorizing the entries 
in your log file. Each log level represent some severity. 

1. ALL : Basically union of all other levels. Everything is going to be logged.
2. DEBUG : It includes information in a very granular way so developers—and other IT professionals—can then use 
that information to perform diagnostics on the application.
3. INFO : We use the INFO logging level to record messages about routine application operation. 
4. WARN : The 'WARN' level designates potentially harmful occurrences.
5. ERROR : The ERROR level denotes a serious problem that has to be dealt with. Not as serious as FATAL.
6. FATAL : The fatal level, like ERROR, designates a problem. But unlike ERROR, it designates a really serious error event.
7. OFF : With this level, nothing gets logged at all.
8. TRACE : This level logs information in a very fine-grained way.

Consider that as the following rank order for the levels:
`ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF`

So if, for instance, the logging framework level is set to WARN, requests with any of the levels WARN, FATAL,
and ERROR will be accepted, while the rest will be denied.

We can set our logging level by using application.properties/application.yml file or using xml configuration files.

```properties
# application.properties 
logging.level.root=WARN
logging.level.com.sabu=TRACE
```

```xml
<logger name="org.springframework" level="INFO" />
<logger name="com.sabu" level="INFO" />
```
If the log level for a package is defined multiple times using the different options, but with different log levels, 
the lowest level will be used. For Example, if we used both way above, log level will TRACE for _com.sabu_ package.

## LOG4J ##

Log4j has three principles:
- Logger : It is responsible for logging information.
- Appenders : It is used to deliver LogEvents to their destination. It decides what will happen with log information. 
In simple words, it is used to write the logs in file. Following are few types of Appenders:
1. ConsoleAppender logs to standard output
2. File appender prints logs to some file
3. Rolling file appender to a file with maximum size
- Layout : It is responsible for formatting logging information in different styles.

```properties
#log4j.properties
log4j.rootLogger=DEBUG,file,stdout

# Direct log messages to stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
# log messages to a file
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=/home/f1soft/Desktop/Selenium.logs
log4j.appender.file.maxFileSize=900KB
log4j.appender.file.maxBackupIndex=5
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
log4j.appender.file.Append=false
```

The following are the description of the pattern appearing the log4j.properties file.
%5p – It writes the level in the log. The “5” in the “%5p” is to set the width of the field to 5 characters.
%d{yyyy-MM-dd HH:mm:ss.SSS}; – It writes the date in the given date-time format.
%t – It writes the method name in the log.
%c – It writes the absolute class name (e.g.com.stackify.log4j_demo.App) in the log.
%m%n – It writes the message in the log.
%L – It writes the line number in the log.
%F – It writes the class name in the log.

For using log4j exclude the default logging provided by  the starter and add log4j dependency

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    
    <!-- other dependencies -->
    <!-- If using Lombok slf4j dependency is not required-->
    <dependency>
      	<groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
   </dependency>

    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
    </dependency>
</dependencies>
```

Then add a log4j.properties file to include configurations

```properties
log4j.rootLogger=DEBUG,file,stdout

# Direct log messages to stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
# log messages to a file
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=/home/f1soft/Desktop/logs/myLogs.log
log4j.appender.file.maxFileSize=900KB
log4j.appender.file.maxBackupIndex=5
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
log4j.appender.file.Append=true

log4j.logger.devpinoyLogger=DEBUG, dest1
log4j.appender.dest1=org.apache.log4j.RollingFileAppender
log4j.appender.dest1.maxFileSize=900KB
log4j.appender.dest1.maxBackupIndex=6
log4j.appender.dest1.layout=org.apache.log4j.PatternLayout
log4j.appender.dest1.layout.ConversionPattern=%d{dd/MM/yyyy HH:mm:ss} %c %m%n
log4j.appender.dest1.File=/home/f1soft/Desktop/Manual.logs
log4j.appender.dest1.Append=false
```

References: 

- https://stackify.com/logging-levels-101/
- https://www.javadevjournal.com/spring-boot/logging-application-properties/
- https://examples.javacodegeeks.com/enterprise-java/log4j/log4j-specific-file-location-example/
- https://stackify.com/log4j-guide-dotnet-logging/