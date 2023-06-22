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
