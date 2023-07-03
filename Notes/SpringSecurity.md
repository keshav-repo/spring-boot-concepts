# Spring security 

Spring Security is a powerful and highly customizable security framework for Java applications, particularly those built on the Spring Framework. It provides a comprehensive set of security features to secure web applications, RESTful APIs, and other types of applications.

Spring Security offers a wide range of security functionalities, including:

1. Authentication: Handles user authentication and identity management, supporting various authentication mechanisms such as username/password, token-based authentication, LDAP, and more.

2. Authorization: Controls access to resources based on user roles and permissions. It allows fine-grained access control, including method-level security and URL-based security rules.

3. Security Filters: Intercepts and processes HTTP requests, applying security checks and enforcing authentication and authorization rules.

4. Session Management: Manages user sessions, including session creation, expiration, and invalidation. It also provides support for handling session-related attacks such as session fixation and CSRF (Cross-Site Request Forgery).

5. Remember-Me Functionality: Enables "Remember Me" functionality for persistent logins, allowing users to authenticate once and be remembered across sessions.

6. Security Events and Auditing: Provides mechanisms to track and log security-related events and perform auditing activities.

7. Integration with other frameworks: Seamlessly integrates with other Spring projects and frameworks, such as Spring MVC, Spring Boot, and Spring Data.

Spring Security is highly flexible and can be customized to fit specific security requirements. It offers a declarative configuration approach using XML or Java-based configuration, and it supports integration with external identity providers, single sign-on (SSO) solutions, and custom authentication and authorization providers.

Overall, Spring Security is widely used in enterprise applications to ensure the security of sensitive information and protect against unauthorized access and attacks.

### Important classes in spring security ?
- AuthenticationManager: This class is responsible for authenticating users. It takes a username and password as input and returns an Authentication object if the user is authenticated.
- **UserDetailsService**: This interface provides a way to load user details from a data source. Spring Security uses the UserDetailsService to load user details when authenticating users.
- WebSecurityConfigurerAdapter: This class is a base class for configuring Spring Security. It provides methods for configuring authentication, authorization, and other security features. Not in spring boot 3
- **SecurityFilterChain**: This class is responsible for filtering requests and applying security constraints. It is used by Spring Security to secure web applications.
- AuthenticationTokenFilter: This class is a filter that is used to authenticate requests. It takes a request as input and returns an Authentication object if the request is authenticated.

### Internal Working

- **Authentication**: Spring Security uses the **AuthenticationManager** to authenticate users. The AuthenticationManager takes a username and password as input and returns an Authentication object if the user is authenticated.
```
public interface AuthenticationManager {
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
```
- Authorization: Spring Security uses the **AccessDecisionManager** to authorize requests. The AccessDecisionManager takes a request and a list of roles as input and returns a decision about whether the request is authorized.
- Filtering: Spring Security uses a chain of filters to filter requests. The filters in the chain are responsible for applying security constraints to requests.
- **Session Management**: Spring Security uses the **SessionManagementFilter** to manage sessions. The SessionManagementFilter ensures that users are only authenticated for a single session.
- **Remember-Me**: Spring Security uses the **RememberMeAuthenticationFilter** to implement remember-me functionality. The RememberMeAuthenticationFilter allows users to be authenticated without having to enter their username and password every time they visit the application.
- [spring security docs](https://docs.spring.io/spring-security/reference/servlet/architecture.html)
- [form login architecture](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html)
- [Internal working](https://riteshpanigrahi.hashnode.dev/spring-security-architecture-and-internal-workflow)

![](https://cdn.hashnode.com/res/hashnode/image/upload/v1647611545607/aySdKZwVz.png?auto=compress,format&format=webp)

### Classes Implementing AuthenticationManager

In Spring Security, the `AuthenticationManager` interface is responsible for authenticating user credentials. It is a core component that handles the authentication process in the security framework. The interface defines a single method called `authenticate()` that takes an `Authentication` object as input and returns an `Authentication` object as output.

Several classes provide implementations of the `AuthenticationManager` interface in Spring Security. Some of the commonly used classes are:

1. `ProviderManager`: This is the default implementation of `AuthenticationManager` in Spring Security. It delegates the authentication process to a list of `AuthenticationProvider` objects. Each provider is responsible for authenticating against a specific user store or authentication mechanism.

2. `DaoAuthenticationProvider`: This is an `AuthenticationProvider` implementation that works with a `UserDetailsService`. It retrieves user details from a data source (such as a database) and performs authentication using the provided credentials.

3. `JwtAuthenticationProvider`: This is an `AuthenticationProvider` implementation for handling JSON Web Tokens (JWT) authentication. It verifies the authenticity and validity of the token and extracts the user details from it.

4. `LdapAuthenticationProvider`: This is an `AuthenticationProvider` implementation for authentication against an LDAP (Lightweight Directory Access Protocol) server. It authenticates users based on their LDAP credentials.

5. `JaasAuthenticationProvider`: This is an `AuthenticationProvider` implementation for authentication using the Java Authentication and Authorization Service (JAAS). It delegates the authentication process to the JAAS framework.
6. Custom implementation of AuthenticationManager
```
public class CustomAuthenticationManager implements AuthenticationManager {
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Implement your authentication logic here
        // Perform authentication checks, such as verifying username and password
        
        // Return an authenticated Authentication object if authentication is successful
        // Otherwise, throw an AuthenticationException or return null
    }
}
```

These are just a few examples of classes that implement the `AuthenticationManager` interface in Spring Security. Depending on your specific requirements and authentication mechanisms, you can choose the appropriate implementation class or create custom implementations by extending the `AuthenticationManager` interface.

#### Note:
By default, Spring Security configures a single DaoAuthenticationProvider as the authentication provider in the ProviderManager. The DaoAuthenticationProvider works with a UserDetailsService to retrieve user details from a data source (such as a database) and performs authentication using the provided credentials.

### Session management
Once you have got an application that is authenticating requests, it is important to consider how that resulting 
authentication will be persisted and restored on future requests.

Commonly used options in session management.
In Spring Boot Security, the `HttpSession` related configuration options provide different options for session management. Some of the commonly used options are:

1. `sessionCreationPolicy`: This option determines when Spring Security should create a new session. It can have the following values:
    - `IF_REQUIRED` (default): A new session is created if required (e.g., when authentication is required).
    - `NEVER`: Spring Security will not create a new session but will use an existing session if available.
    - `STATELESS`: Spring Security will not create or use a session, and each request will be treated as independent.

2. `sessionFixation`: This option configures how Spring Security handles session fixation attacks. It can have the following values:
    - `NONE` (default): No session fixation protection is applied.
    - `CHANGE_SESSION_ID`: The session ID is changed after successful authentication.
    - `MIGRATE_SESSION`: The session ID is changed and session attributes are migrated to the new session after successful authentication.
    - `new Session`: 
   
```
          http
             .sessionManagement(
                  session-> session
                           .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

    To set five minutes for session expire
    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }
    
    private static class SessionListener implements HttpSessionListener {
    
        @Override
        public void sessionCreated(HttpSessionEvent event) {
            HttpSession session = event.getSession();
            session.setMaxInactiveInterval(300); // 5 minutes
        }
    
        @Override
        public void sessionDestroyed(HttpSessionEvent event) {
            // Session destroyed event handling
        }
    
    }
```

3. `invalidSessionUrl`: This option specifies the URL to redirect to when an invalid session is detected (e.g., session timeout or invalid session ID).

4. `maximumSessions` and `maxSessionsPreventsLogin`: These options control the behavior when multiple sessions are detected for the same user. `maximumSessions` sets the maximum number of sessions allowed per user, and `maxSessionsPreventsLogin` determines whether to prevent login when the maximum number of sessions is reached.

5. `expiredUrl`: This option specifies the URL to redirect to when a session has expired.

6. `sessionConcurrency`: This option allows you to configure session concurrency control. It can have the following values:
    - `CONCURRENT_SESSIONS` enables concurrent session control.
    - `MAX_SESSIONS_PREVENTS_LOGIN` prevents login when the maximum number of sessions is reached.
    - `NONE` disables session concurrency control.


### how session cookies is stored in spring security ?
In Spring Security, session cookies are typically stored in the **client's browser as HTTP cookies**. When a user logs in or establishes a session with the application, a session cookie is created and sent back to the client. The client then includes this session cookie in subsequent requests to the server to maintain the session.

The session cookie contains a **unique session identifier** that allows the server to identify and associate the request with the correct session data. The server retrieves the session data associated with the session identifier stored in the cookie and uses it to maintain the user's session state.

Spring Security provides various options for managing and storing session cookies. By default, **Spring Security uses an in-memory session management mechanism** where session data is stored on the server's memory. However, this is not suitable for distributed or clustered environments.

To store session cookies in a more scalable and distributed manner, you can configure Spring Security to use external session stores such as **Redis, JDBC, or Hazelcast.** This allows session data to be stored outside the application server, making it accessible to multiple instances of the application.

By leveraging external session stores, you can achieve features like session replication, session failover, and session sharing across multiple instances of the application, improving scalability, and fault tolerance.

It's important to choose an appropriate session storage mechanism based on your application's requirements, scalability needs, and infrastructure setup.








