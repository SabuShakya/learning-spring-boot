# Spring Web MVC Workflow #

![Alt text](./springMvcWorkflow.png?raw=true "Title")

1. The request will be received by the DispatcherServlet or the Front Controller.
2. Then the DispatcherServlet will forward the request to HandlerMapping, which will suggest to which Controller 
the request is to be sent. HandlerMapping will pay particular attention to the request URL and suggest Controller 
based on that.
3. Now the request is transferred to the Controller. The Controller processes the request by executing some logical 
methods and prepares data(ModelAndView object- contains Model data and view name).
4. The controller now returns the Model and View name back to the DispatcherServlet.
5. The DispatcherServlet will further pass the View name to the view resolver to get the actual view page.
6. Finally, the DispatcherServlet will pass the View with Model object to display the result.

Reference:

- [Gontu Series Spring MVC](https://www.youtube.com/watch?v=qHllF5pl1PA&ab_channel=gontuseries)

## Servlets ##
Java Object that has implementations of various request handling methods such as doGet,doPost and on which responds to
HTTP requests. It is simply a Java technology that  handles the incoming HTTP requests. 
Servlets run in a servlet container which handles the networking side (e.g. parsing an HTTP request,
connection handling etc). One of the best-known open source servlet containers is Tomcat.

References:
- [Section 2 Module 1 Part 1: What are Servlets?](https://www.youtube.com/watch?v=05iLCEZxPvI&t=16s&ab_channel=JulesWhite)

## DispatcherServlet ##
It is the class which manages the entire request handling process. Like a normal servlet, it also needs to be configured
on the web deployment Descriptor(web.xml).

