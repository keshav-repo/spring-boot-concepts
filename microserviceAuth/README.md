1. Start container using below command

```shell
docker run -d --name keycloak -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.1 start-dev
```

2. Go to Adminstrative Console and use below cred

Username: admin
Password: admin

3. Create a new realm using dropdown and clicking on Create Realm.

4. Create a new client with id as `test-client`, Client Protocol as `openid-connect` and root url as http://localhost:8081
