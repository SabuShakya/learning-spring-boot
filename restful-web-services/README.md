# Web Services #

A Web service is a collection of open protocols and standards which are widely used for exchanging data between systems 
or applications.

Web services are client and server applications that communicate over the World Wide Web's(WWW) HTTP.
It provides a standard means of interoperating  between software applications running on variety of platform and 
frameworks. 
Web Service is a standardized medium to propagate communication between client and server applications on World Wide Web.
 
 
## How Web Services work? ##

![Alt text](./webservices.png?raw=true "Title")

The client invokes a series of web service calls via requests to a server which will be hosting the actual web service.
These requests are made through remote procedure calls(RPC). The main component of a web service design is the data which
is transferred between the client and server, that is XML(Extensible markup language). This provides a platform for 
application developed in various programming languages to talk to each other.

## Why do we need Web Service? ##

Modern day business applications use variety of programming platforms to develop web-based applications. Some may use 
Java,others NodeJs, while other may use .Net,Angular or any other platform. These heterogeneous applications need some 
sort of communication to happen between them. It becomes difficult to ensure accurate communication between these 
applications as they are built in different development languages.
This is where web services come in. Web services provide a common platform that allows multiple application built on
various  programming languages to communicate with each other.

*`Advantages`*

- Exposing Business Functionality on the network
- Interoperability amongst applications.
- A Standardized Protocol which everybody understands.
- Reduction in cost of communication.

## Types of Web Services ##
 
 Mainly two types:
 1. SOAP web services
 2. RESTful web services
 
 ### SOAP (Simple Object Access Protocol) ###
 
 It is based on transferring XML data as SOAP messages.Each message has something known as XML document. The client 
 application requires some basic information regarding what the server does, so that it can invoke the right web wervice.
 This is done with the help of WDSL(Web Services description language). The WDSL is again an XML-based file which tells 
 the client application what the web service does. By using the WSDL, the client will be able to understand where the 
 web service is located and how it can be utilized.
 
 
 ### RESTful Web Services ### 
 
 It is a lightweight, maintainable, and scalable service which is built on the REST architecture.
 REST stands for Representational State Transfer, which is an architectural style for providing standards between 
 computer systems on web,making it easier for them to communicate with each other. RESTful systems are characterized 
 by how they are stateless and separate the concerns of client and server.
 In REST architectural style, data and functionality are accessed using Uniform Resource Identifiers(URIs).
 
 `Separation of Client And Server`: In REST architectural style, the implementation of the client and the implementation 
 of the server can be done independently without each knowing about the other. As long as  each side knows what format 
 of messages to send to the other, they can be kept modular and separate. Separating the user interface concerns from 
 data storage concerns, we improve the flexibility of the interface across platforms and improve scalability by simplifying
 the server components.
 By using a REST interface, different clients hit the same REST endpoints, perform the same actions, and receive 
 the same responses.
 
 `Statelessness`:  Systems that follow REST guidelines are stateless, meaning the server doesn't need to know about
 what state the client is in and vice versa. In this way the client and server can understand any message received,
 even without seeing previous messages. The constraint of statelessness is enforced through the use of resources. 
 Resources are any object, document or thing that needs to be stored or send to other services. 

REST requires client to make a request to the server inorder to retrieve or modify data on the server. A request consists
of:
- an HTTP verb (Request type - HTTP GET/POST/PUT/DELETE)
- a header, which allows client to pass information about the request.
- a path to a resource
- optional message body containing data


 References:
 - [What are Web Services? Architecture, Types, Example](https://www.guru99.com/web-service-architecture.html)
 - [What is REST?](https://www.codecademy.com/articles/what-is-rest)