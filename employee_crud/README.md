### To run locally

```shell
docker run --name mysql-server -e MYSQL_ROOT_PASSWORD=secret@123 -p 3300:3306 -d mysql:latest

# change value spring.datasource.url in properties file to 
spring.datasource.url=jdbc:mysql://localhost:3300/employee

# access db
mysql -h 127.0.0.1 -P 3300 -u root -psecret@123

# create database employee
create database employee;
use employee;
```
