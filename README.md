# spring-oauth2-reactive-with-mongo

Custom login example with Spring social oauth2 (facebook, github, ...) + reactive + mongo-session

<br>


### Custom Login Flow 

1. Access to login-required resource-url. And the access denied. (if you are not authenticated) 
2. Authenticate through Spring social OAuth2 (facebook, github) and acquire oauth2 access token from social provider. 
3. If authenticated and authorized, generate JWT, custom-token. And redirect to the resource-url.
4. Authenticated and authorized through custom-token(JWT) login-process.

> OAuth2 authentication is only for custom-token(JWT) generation.
> - OAuth2 authentication run with spring mongo session.  

<br>

### Run Example

Edit config file 

```yaml
spring:
  thymeleaf:
    cache: false
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: {your github client-id}
            client-secret: {your github client-secret}
          facebook:
            client-id: {your facebook client-id}
            client-secret: {your facebook client-secret}
  session:
    store-type: mongodb
    mongodb:
      collection-name: "sessions"
  data:
    mongodb:
      uri: "mongodb://localhost:27017/jlog"
```



1. Run MongoDB - ```docker run --name spring-mongo -p 27017:27017 -d mongo:4.2.0-bionic```  
2. Run Java Example
3. Move to ```http://localhost:8080/me```, you can see your email address. 


<br>


### Reference 
- [Spring Boot Security OAuth2 social login](https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-2/)