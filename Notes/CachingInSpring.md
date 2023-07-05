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
1. Cacheable
```
 @Cacheable(key = "#id",value = "Product",unless = "#result.price > 50000")
 public Product findProduct(@PathVariable int id) {
```
2. CacheEvict
```
    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id) {
```
3. CachePut
4. EnableCaching
5. 

### Best practice to put caching logic in 

### How caching data will work in distributed system ? Specifically in case of redis cache. 


Resources: 
- https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/
- 
