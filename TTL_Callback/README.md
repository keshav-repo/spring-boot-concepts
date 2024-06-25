## Redis setup docker

```shell
# Run local instance of docker and expose port 6379
docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest

# enter into redis 
redis-cli -h 127.0.0.1 -p 6379

# set key value for 60 second
SET key1 value1 EX 30
SET key2 value2 EX 60

```
