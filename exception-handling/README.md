# Exception Handling #

Spring boot by default gives us exception handling. But the details given by it interests more to developers rather
than user. So we can add custom exception handlers to provide a customized response to the users.
Let us first look at some common annotations used for exception handling.

### @ResponseStatus ###
-> It allows us to modify the HTTP status of our response. It can be applied to:
	- On the exception class itself
	- Along with the @ControllerAdvice annotation on classes
	- Along with the @ExceptionHandler annotation on methods

### @ExceptionHandler ###
-> The annotation can be used in methods of class annotated with @Controller or @ControllerAdvice.
-> It provides a lot of flexibility for handling exceptions.The exception handler method takes in an exception or a list 
of exceptions as an argument that we want to handle in the defined method.
If we don’t wish to use these annotations, then simply defining the exception as a parameter of the method will also do:

### @ExceptionHandler ###
public ResponseEntity<String> handleNoSuchElementFoundException( NoSuchElementFoundException exception)

### @ControllerAdvice ###
->  @ControllerAdvice is an annotation provided by Spring allowing you to write global code that can be applied to a 
wide range of controllers — varying from all controllers to a chosen package or even a specific annotation.
By default, @ControllerAdvice will apply to all classes that use the @Controller annotation (which extends to
classes using @RestController). 

### @RestControllerAdvice ###
-> @RestControllerAdvice is just a syntactic sugar for @ControllerAdvice + @ResponseBody. @RestControllerAdvice
annotation tells a controller that the object returned is automatically serialized into JSON and passed it to the 
HttpResponse object.

### ResponseEntityExceptionHandler ###
-> ResponseEntityExceptionHandler is a convenient base class for controller advice classes. It provides exception 
handlers for internal Spring exceptions. If we don’t extend it, then all the exceptions will be redirected to 
DefaultHandlerExceptionResolver which returns a ModelAndView object.

Create a separate package for exceptions.

![Alt Text](./exceptionFolder.jpg?raw=true "Folder")

We will be implementing a simple custom exception. For that, we will create a GlobalExceptionHandler class, 
a MyCustomException class and a GenericErrorResponse.

```java
package com.sabu.exceptionhandling.exception;

public class MyCustomException extends RuntimeException{
    public MyCustomException(String message) {
        super(message);
    }
}
```

```java
package com.sabu.exceptionhandling.exception.exceptionHandler;

import com.sabu.exceptionhandling.dto.GenericErrorResponse;
import com.sabu.exceptionhandling.exception.MyCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MyCustomException.class})
    public ResponseEntity<?> myCustomErrorHandler(MyCustomException exception){
        GenericErrorResponse response = new GenericErrorResponse(exception.getMessage(), "CODE_TEST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
```

```java
package com.sabu.exceptionhandling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericErrorResponse {

    private String message;

    private String code;

}
```

We then create a simple controller method that throws this exception.

```java
package com.sabu.exceptionhandling.controller;


import com.sabu.exceptionhandling.exception.MyCustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/test")
    public String testException(){
        throw new MyCustomException("This is a custom exception as bad request.");
    }
}

```
We run the application and try accessing the "/test" api.

![Alt Text](./exceptionResult.jpg?raw=true "result")

Our GlobalExceptionHandler will only look at the exceptions thrown by controllers. They won't be handling the exceptions 
thrown by the Filters.
If we need to throw the custom error from the filter,like authentication filter of Jwt, we can use HandlerExceptionResolver
to resolve the exception to our global exception controller.

```java
import com.nimbusds.jwt.JWTClaimsSet;
import com.sabu.springbootjwt.exception.UnauthorizedException;
import com.sabu.springbootjwt.service.UserAuthenticationService;
import com.sabu.springbootjwt.util.TokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * this filter is going to intercept each request only once
 * */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    public JwtRequestFilter(TokenUtil tokenUtil, UserAuthenticationService userAuthenticationService) {
        this.tokenUtil = tokenUtil;
        this.userAuthenticationService = userAuthenticationService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String jwtToken = null;
        String username = null;
        JWTClaimsSet jwtClaimsSet = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.replace("Bearer ", "");
            try {
                jwtClaimsSet = tokenUtil.parseEncryptedToken(jwtToken);
            } catch (Exception e) {
                handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null,
                        new UnauthorizedException("Token not valid."));
                // this exception will then go to our exception handler
                return;
            }
            username = jwtClaimsSet.getSubject();

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userAuthenticationService.loadUserByUsername(username);
                if (tokenUtil.validateToken(jwtClaimsSet, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

```