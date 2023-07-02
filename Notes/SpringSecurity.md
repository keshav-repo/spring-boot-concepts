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

















