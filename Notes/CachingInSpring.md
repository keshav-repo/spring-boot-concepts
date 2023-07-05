# Caching

Caching is an important technique for improving the performance and scalability of applications, including Spring Boot applications. Caching helps reduce the load on the underlying resources, such as databases or external services, by storing frequently accessed data in memory. 

In a Spring Boot application, you can implement caching using the caching support provided by the Spring Framework. Spring's caching support integrates with various caching providers, such as Ehcache, Caffeine, Hazelcast, Redis, etc

### Redis cli commands
```
1. Get all keys 
    keys * 
2. Get partidular key 
    get Product::3
3. 
```

### Annotations 
1. **Cacheable**:
The Cacheable annotation is a feature provided by the Spring Framework for caching method results. By applying the @Cacheable annotation to a method, Spring caches the method's return value for a given set of parameters. Subsequent invocations of the method with the same parameters can be intercepted and the cached result is returned without executing the method body.
```
 @Cacheable(key = "#id",value = "Product",unless = "#result.price > 50000")
 public Product findProduct(@PathVariable int id) {
```
2. **CacheEvict**
 Annotation indicating that a method (or all methods on a class) triggers a cache evict operation.
 
```
    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id) {
```
3. CachePut:
   Annotation indicating that a method (or all methods on a class) triggers a cache put operation.
4. EnableCaching:
   When you annotate your configuration class with @EnableCaching annotation, this triggers a post processor that would scan every Spring bean for the presence of caching annotations on public methods. If such an annotation is found, a proxy is automatically created to intercept the method call and handle the caching behavior accordingly.
5. 

### Best practice to put caching logic in 

### How caching data will work in distributed system ? Specifically in case of redis cache. 
Distributed caching with Redis and Spring Boot typically involves using Redis as a shared cache across multiple instances of a Spring Boot application. Each application instance can read and write cached data to the shared Redis cache, providing a scalable and consistent caching solution.

### Scaling Redis

To scale Redis for a distributed system, you can employ various techniques and configurations based on your specific requirements and workload. Here are some approaches to consider:

1. Redis Cluster: Redis Cluster is a built-in distributed solution provided by Redis itself. It allows you to create a cluster of multiple Redis nodes, each responsible for a subset of the data. Redis Cluster provides automatic data sharding and high availability by replicating data across multiple nodes. It offers partitioning, fault tolerance, and automatic resharding capabilities. You can configure a Redis Cluster by setting up multiple Redis instances and using the Redis Cluster configuration file.

2. Redis Sentinel: Redis Sentinel is another built-in feature of Redis that provides high availability for Redis instances. It monitors the health of Redis nodes and automatically promotes a replica to a master in case of a failure. Redis Sentinel allows you to configure multiple Redis instances, with one acting as the master and others as replicas. It provides automatic failover and can handle Redis instance discovery. Redis Sentinel requires minimal configuration changes in your Redis setup.

3. Redis Replication: Redis supports replication, where one Redis instance acts as the master and other instances act as replicas. Replication provides data redundancy and read scalability. The master instance accepts write operations, while the replicas replicate the data from the master and handle read operations. You can configure Redis replication by setting up a master-slave relationship between Redis instances in the Redis configuration file.

4. Redis Sharding: Redis can be sharded manually by partitioning your data across multiple Redis instances. Sharding involves dividing your data into multiple shards based on a specific criteria (e.g., by key ranges or using a consistent hashing algorithm). Each Redis instance is responsible for a subset of the data. To perform sharding, you need to handle the data distribution and routing logic in your application code.

5. Client-Side Sharding: In a client-side sharding approach, your application takes responsibility for sharding the data across multiple Redis instances. The application logic determines which Redis instance to connect to based on the key or data distribution algorithm. This approach gives you more flexibility and control over the sharding strategy but requires additional development effort.

6. Redis Cluster + Redis Sentinel: You can combine Redis Cluster and Redis Sentinel to achieve both horizontal scalability and high availability. Redis Cluster provides data sharding and partitioning, while Redis Sentinel monitors and manages the cluster's health. This combination offers automatic resharding, failover, and fault tolerance in a distributed Redis setup.

When scaling Redis in a distributed system, it's essential to consider factors such as data consistency, network latency, fault tolerance, and the specific requirements of your application. Choose the scaling approach that aligns best with your needs, and consider load balancing, proper configuration, monitoring, and testing to ensure the successful scalability of your Redis infrastructure.

### Caching option in AWS
- Amazon ElastiCache for Redis: Amazon ElastiCache is a fully managed Redis service provided by AWS. It allows you to easily deploy and manage Redis clusters in the cloud. You can configure your Spring Boot application to use ElastiCache for Redis as the caching provider. ElastiCache offers high performance, scalability, and automatic failover, making it a popular choice for caching in AWS.
- Amazon ElastiCache for Memcached: Another caching option provided by AWS is Amazon ElastiCache for Memcached. It is a fully managed Memcached service that provides in-memory caching capabilities. You can configure your Spring Boot application to use ElastiCache for Memcached as the caching provider. Memcached is commonly used for simple key-value caching scenarios.

### Why Amazon ElastiCache for Redis. 
One of the popular caching options in AWS for Spring Boot REST services is Amazon ElastiCache for Redis. ElastiCache for Redis is widely used for caching in AWS environments **due to its high performance, scalability, and flexibility**. Here are some reasons why ElastiCache for Redis is a popular choice:

1. In-Memory Caching: Redis is an in-memory data store, which makes it highly efficient for caching frequently accessed data. It provides fast read and write operations, reducing the latency compared to disk-based storage solutions.

2. Fully Managed Service: ElastiCache for Redis is a fully managed service provided by AWS. It takes care of tasks such as cluster deployment, scaling, patching, and monitoring, relieving you from the operational overhead of managing a Redis cluster.

3. Scalability and High Availability: ElastiCache for Redis allows you to scale your cache horizontally by adding or removing nodes. It provides automatic data partitioning and replication, ensuring high availability and fault tolerance.

4. Data Persistence Options: Redis offers different data persistence options, such as snapshotting and append-only file (AOF) persistence. You can configure ElastiCache for Redis to persist data to disk, providing durability in case of node failures or restarts.

5. Advanced Data Structures and Operations: Redis supports a wide range of data structures such as strings, lists, sets, sorted sets, and hashes. It also provides advanced operations like atomic counters, pub/sub messaging, and geospatial indexing, giving you more flexibility in caching scenarios.

6. Integration with Spring Boot: Spring Boot provides excellent integration with Redis through its Spring Data Redis module. It offers annotations like `@Cacheable`, `@CachePut`, and `@CacheEvict` to simplify cache configuration and usage in Spring Boot applications.

#### what does it mean if Network Performance is Up to 5 Gigabit (5 GiB)?
If a network performance is advertised as "up to 5 Gigabit," it means that the network infrastructure is capable of delivering a maximum throughput of 5 Gigabits per second (Gbps). This measurement refers to the data transfer rate or bandwidth capacity of the network.

#### Cache eviction policy
- LRU (Least Recently Used)
- LFU (Least Frequently Used)
- MRU (Most Recently Used)
- Random Replacement

#### Eviction policy for Local redis 
In Redis, you can set the eviction policy using the `maxmemory-policy` configuration option. This option determines how Redis should handle evictions when the memory limit specified by `maxmemory` is reached. Here are the steps to set the eviction policy in Redis:

1. Open the Redis configuration file:
    - If you're using Redis in a local environment, the configuration file is typically located at `/etc/redis/redis.conf`.
    - If you're using Redis in a Docker container, you can mount the configuration file as a volume and edit it using a text editor.

2. Locate the `maxmemory-policy` directive:
   In the Redis configuration file, search for the `maxmemory-policy` directive. By default, it may be commented out or set to `noeviction`, which means that Redis won't evict any keys when the memory limit is reached.

3. Choose an eviction policy:
   Uncomment the `maxmemory-policy` directive and set it to one of the available eviction policies according to your requirements. Here are some commonly used eviction policies:

    - `noeviction`: This policy disables eviction. Redis will return an error if memory limit is reached and no space is available.
    - `volatile-lru`: This policy evicts the least recently used keys with an expiration (TTL) set. It prioritizes evicting keys with a shorter remaining TTL.
    - `allkeys-lru`: This policy evicts the least recently used keys regardless of whether they have an expiration (TTL) set or not.
    - `volatile-lfu`: This policy evicts the least frequently used keys with an expiration (TTL) set.
    - `allkeys-lfu`: This policy evicts the least frequently used keys regardless of whether they have an expiration (TTL) set or not.
    - `volatile-random`: This policy evicts random keys with an expiration (TTL) set.
    - `allkeys-random`: This policy evicts random keys regardless of whether they have an expiration (TTL) set or not.
    - `volatile-ttl`: This policy evicts keys with an expiration (TTL) set based on the remaining time to live.

   Choose the appropriate eviction policy based on your application's requirements and memory management strategy.

4. Save and restart Redis:
   Save the changes to the Redis configuration file and restart Redis to apply the new eviction policy. The steps to restart Redis depend on your operating system or deployment environment. For example, you might use the command `sudo service redis restart` on Linux.

After setting the eviction policy, Redis will automatically evict keys based on the specified policy when the memory limit is reached. Be aware that eviction affects only the key space, not the individual fields within a hash or the items in a list or set.


Resources: 
- https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/
- 
