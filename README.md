# api-gateway-identity

### Realisation of User Profile application
* POST /api/v1/identity/user/registration - registration of user in memory storage (username/password)
* POST /api/v1/identity/user/login - user login with creds
* GET /api/v1/identity/user/{userId}  - get user profile
* PUT /api/v1/identity/user/{userId}  update user profile

##NB:
**After success registration Service produce message via Apache Kafka to Billing Service (async) to create account**

## Postman Collection

    docker-compose up
    newman run identity.postman_collection.json

