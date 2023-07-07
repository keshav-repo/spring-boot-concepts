# Eureka Server

Eureka Server is a component of Netflix's open-source service discovery framework called Eureka. It is designed to enable automatic registration and discovery of microservices within a cloud-based architecture. Eureka Server provides a registry where microservices can register themselves and discover other services in the system.

Here are some key features and concepts related to Eureka Server:

1. Service Registration:
   Microservices running in a cloud environment can register themselves with the Eureka Server. When a microservice starts up, it sends a registration request to the Eureka Server, providing its unique identifier, network location, and other metadata. The Eureka Server maintains a registry of all registered services.

2. Service Discovery:
   Microservices that need to communicate with other services can query the Eureka Server to discover the network locations of those services. This allows services to dynamically locate and communicate with other services without hard-coding their network addresses.

3. High Availability and Resilience:
   Eureka Server supports high availability and resilience through its peer-to-peer architecture. Multiple instances of the Eureka Server can be deployed, forming a cluster. Each server instance registers itself with other instances in the cluster, and they share the registry information. This redundancy ensures that even if one Eureka Server instance goes down, the service registry remains available.

4. Heartbeat and Health Monitoring:
   Eureka Server uses heartbeat mechanisms to monitor the health and availability of registered services. Each registered service sends periodic heartbeat signals to the Eureka Server to indicate that it is still alive and functioning. If a service fails to send heartbeats within a certain threshold, the Eureka Server considers it unavailable and removes it from the registry.

5. Load Balancing and Client-Side Load Balancers:
   Eureka Server provides service load balancing capabilities. Client applications can use a client-side load balancer, such as Netflix Ribbon, which integrates with Eureka, to distribute requests among multiple instances of a service. The load balancer retrieves the available instances of a service from the Eureka Server and applies load-balancing algorithms to distribute requests effectively.

Eureka Server simplifies the architecture and management of microservices by providing a centralized service registry and discovery mechanism. It enables developers to build scalable and resilient applications in a cloud-based environment, where services can be dynamically added, removed, or scaled up/down without manual intervention.

### How load balancing works in eureka server


## Spring Cloud LoadBalancer 

### Client Side Load Balancing 
- Spring Cloud LoadBalancer with Eureka Server for client-side load balancing. 
- Netflix Ribbon (Under maintenance)

### Server Side load balancing
- Spring Cloud Gateway
- Netflix Zuul

#### Load balancing algorithms provided by  Spring Cloud LoadBalancer
- Round Robin: Requests are distributed evenly across available instances in a cyclic manner.
- Random: Requests are randomly assigned to available instances.
- Weighted Round Robin: Each instance is assigned a weight, and requests are distributed proportionally based on the weight assigned to each instance.
- Weighted Response Time: Instances with lower response times are assigned a higher weight, leading to more requests being directed to faster instances.
- Availability Filter: Instances that are marked as "down" or experiencing issues are filtered out, and requests are only sent to the healthy instances.

### Custom config of load balancing algorithm 
In properties file 
```
spring.cloud.loadbalancer.ribbon.rule-configurations.my-service-name=ZoneAvoidance/RoundRobin/ Random/WeightedResponseTime

```
In the config file 
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.config.LoadBalancerClientSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerConfiguration {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Bean
    public LoadBalancerClientSpecification loadBalancerClientSpecification() {
        return LoadBalancerClientSpecification.builder()
                .loadBalancerName("my-service-name")
                .loadBalancerType("ZoneAvoidance")
                .build();
    }
}
```

### How to configure eureka netflix in docker/kubernates 



