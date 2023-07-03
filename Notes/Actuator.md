### Actuator

**Spring Boot Actuator** is a feature provided by Spring Boot that allows you to monitor and manage your application at runtime. It provides several built-in endpoints that expose various metrics and information about your application, such as health, metrics, environment, logging, and more.

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

#### Properties to be configured in properties file 
```
management.server.port: 9001
management.server.address: 127.0.0.1
management.endpoints.web.exposure.include=*

management.endpoint.env.show-values=ALWAYS
```

#### Endpoints are
```
http://localhost:9001/actuator/health
http://localhost:9001/actuator/loggers
http://localhost:9001/actuator/metrics
http://localhost:9001/actuator/metrics/jvm.memory.used
http://localhost:9001/actuator/info
```

### Commenly Used Actuator endpoint

1. `/actuator/health`: Provides information about the health status of your application, indicating whether it is up and running or encountering any issues.

2. `/actuator/info`: Returns general information about your application, such as its name, version, and description.

3. `/actuator/metrics`: Gives access to various application metrics, including JVM memory usage, CPU usage, HTTP request metrics, and custom metrics if configured.

4. `/actuator/loggers`: Allows you to view and modify the logging configuration of your application, including the log levels of different loggers.

5. `/actuator/auditevents`: Provides access to audit event information, such as successful and failed authentication attempts.

6. `/actuator/mappings`: Displays a list of all the request mapping endpoints available in your application.

7. `/actuator/env`: Shows the current environment properties, including system properties, environment variables, and application-specific properties.

8. `/actuator/trace`: Gives a trace of recent HTTP requests handled by your application, including details like the request method, URL, headers, and response status.

