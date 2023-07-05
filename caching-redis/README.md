# spring-data-redis-cache

### Populate product 
```
curl --location 'http://localhost:9292/product' \
--header 'Content-Type: application/json' \
--data '{
    "id": 2,
    "name": "Tecno Spark 10 5G",
    "qty": 100,
    "price": 14999
}'
Also pouplate below product using post rest api call 
Endpoint: http://localhost:9292/product
Method: POST
{
    "id": 3,
    "name": "OnePlus Nord CE 3 Lite 5G",
    "qty": 50,
    "price": 19999
},
{
    "id": 0,
    "name": "Redmi A2",
    "qty": 100,
    "price": 6299
}
```

#### Enter into redis cli 
```
docker exec -it <Container-ID> redis-cli
```

