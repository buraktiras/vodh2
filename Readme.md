#Spring Boot Project with in-memory and publish a rest controller

This project has been created for parking system. In this project we have used the rest service with spring boot.

Additional we have use in-memory db which is h2.
You have to follow this step for running project.

1. Open this project IntelliJ with JDK 1.8
2. Generate project with mvn package
3. Open h2 database with http://localhost:8082/h2-console.
   *spring.datasource.username=sa
   *spring.datasource.password=password

4. Open the postman and import additional json file which name is ParkServices.postman_collection
5. As you can find all requests below.
   *http://localhost:8082/leave-vehice
   *http://localhost:8082/status
   *http://localhost:8082/park-vehice