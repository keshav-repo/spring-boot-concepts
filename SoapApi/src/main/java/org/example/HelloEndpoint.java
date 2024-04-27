package org.example;

import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;


@Endpoint
@Component
public class HelloEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/hello";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHelloRequest")
    public GetHelloResponse getHello(GetHelloRequest request) {
        GetHelloResponse response = new GetHelloResponse();
        response.setMessage("Hello, " + request.getName() + "!");
        return response;
    }
}
