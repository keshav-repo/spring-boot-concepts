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


