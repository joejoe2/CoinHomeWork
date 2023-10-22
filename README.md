# Database Initialization

Database will be initialized by `Liquibase` automatically. 
Then the default data will be generated from `src/main/resources/db/script` along with the initialization.

# Test

Run ```./mvnw test```.

Test cases 1 - 6 is located at `src/test/java/com/example/coin`.

# Lint

Run ```./mvnw spotless:apply```