package com.sabu.learning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggerControllerUsingAnnotation {

    @GetMapping("/test")
    public String testingLog(){
        log.info("INFO LOG USING log after annotating");
        log.debug("DEBUG LOG USING log after annotating");
        log.warn("WARN LOG USING log after annotating");
        log.error("ERROR LOG USING log after annotating");
        log.trace("TRACE LOG USING log after annotating");
        return  "Hello from the other side";
    }
}
