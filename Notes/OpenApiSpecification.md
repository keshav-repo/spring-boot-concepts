# OpenAPI Specification

The OpenAPI Specification (OAS), formerly known as Swagger, is an industry-standard specification for describing RESTful APIs. It provides a standardized format for documenting, designing, and consuming APIs. The OpenAPI Specification allows developers to describe the various endpoints, operations, request/response formats, parameters, authentication methods, and other details of an API.

The OpenAPI Specification is written in YAML or JSON format and serves as a contract between API providers and consumers. It provides a machine-readable representation of the API, which can be used by automated tools to generate client SDKs, server stubs, documentation, and perform API testing.

By using the OpenAPI Specification, developers can achieve the following benefits:

- Documentation: The specification serves as comprehensive documentation for the API, describing its structure, endpoints, and expected behaviors.
- Code Generation: The specification can be used to generate client libraries, server stubs, and API documentation, saving development time and ensuring consistency.
- Interoperability: The specification promotes interoperability by providing a standard way to describe and consume APIs. It allows different systems to communicate and integrate seamlessly.
- Testing: The specification can be used to automate API testing, as it defines the expected inputs, outputs, and behavior of the API endpoints.
- Collaboration: The specification serves as a contract that facilitates collaboration between API providers and consumers, ensuring a clear understanding of the API's capabilities and requirements.

### Dependency to be added 
```
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

### Set the ui path in spring boot 
```
springdoc.swagger-ui.path=/swagger-ui.html
```

