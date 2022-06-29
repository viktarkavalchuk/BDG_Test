# The system of Private Announcements Spring MVC Service Example

The project contains example code for an Private Announcement Service that uses the
Spring MVC framework. 


## Building the service

This is a Java 8 project that uses Apache Maven to build. MySQL database version 5.5+ is needed for the project to work.
After the build using 'Script.bat', the `Senla_Java_Final-1.0.war` archive will be created
in the `/target` directory, which can be deployed in an instance of Tomcat 9. 
The "Private_announcements" schema will also be created in your DB.

## Deploying the service

This service makes use of features introduced in Tomcat 9 so deploy this service via the Tomcat 9+ 
administration UI. Once installed ensure it is enabled via the UI, and then you should be able to make a request 
to `http://localhost:8080/Senla_Java_Final-1.0/authenticate`, with JSON body 

{
    "userName": {userName},
    "password": {password}
}

List of users: 

| userName | password  | 
|:--------:|:---------:| 
| Admin    | 123       | 
| User1    | 123       | 
| User2    | 111       | 
| User3    | 111       |  

You will receive a Bearer token.

## Examples API

The API of this service provides a number of endpoints that clients use. 
For the convenience of testing the application and verifying functionality, Swagger/Open API is used at:
http://localhost:8080/Senla_Java_Final-1.0/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

If you run the application through the development environment, use the address:
http://localhost:8080/swagger-ui/index.html#/