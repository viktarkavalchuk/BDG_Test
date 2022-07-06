## BGD Test Spring MVC Service Example

The project contains example code for an BDG Rest API Service that uses the Spring Boot framework. 


## Building the service

This is a Java 8 project that uses Apache Maven to build. MySQL database version 8+ is needed for the project to work.

- Build project using Maven. Generated `.war` file can be deployed in an instance of Tomcat 8,5. 
    ```
    mvn install
    ```
- Run 'Script.bat'. The "bdg" schema will be created in your DB.  
    ```
    ./Script.bat
    ```

## Deploying the service

This service makes use of features introduced in Tomcat 8,5 so deploy this service via the Tomcat 8,5+ 
administration UI. Once installed ensure it is enabled via the UI, and then you should be able to make a request 
to `http://localhost:8080/bdg_api/authenticate`, with JSON body 
```json
{
    "userName": {userName},
    "password": {password}
}
```
List of users: 

| userName | password  | 
|:--------:|:---------:| 
| Admin    | 123       | 
| User     | 123       | 

You will receive a Bearer token.

To use this API run Angular [frontend](https://github.com/viktarkavalchuk/BDG_frontend_angular) project. 