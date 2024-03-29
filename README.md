# Sample application

## This project requires tomcat 8

Download from here <br />```https://archive.apache.org/dist/tomcat/tomcat-8/v8.0.24/bin/```

After installing Tomcat, set the CATALINA_HOME and JAVA_HOME env variables and then run
 <br />``` sh build.sh && deploy.sh ``` 
to build and launch the sample application

Alternatively, to run it using spring-boot embedded tomcat, you could just run the following command in the root of the project:
<br />``` mvn spring-boot:run -Dserver.port=2020 ``` 
Note: port number is optional if the default 8080 is available

This is a super simple JAX-RS RESTEasy Spring Boot application just to exercise RESTEasy Spring Boot starter.<br>



## Testing it

Send a **POST** request message, containing the payload below, to [http://localhost:8080/sample-app/echo](http://localhost:8080/sample-app/echo).

```
    is there anybody out there?
```

You should receive a response message with a payload similar to this as result:

``` json
    {
        "timestamp": "1484775122357",
        "echoText": "is there anybody out there?"
    }
```

The request message payload can be anything as plain text.
The response message is supposed to echo that, plus a timestamp of the moment the echo response was created on the server side. The response message will be in JSON format.
# tracing-shell-app
