# Database Initialization

Database will be initialized by `Liquibase` automatically (or can be manually from `src/main/resources/db/script/init-schema.sql`). 
Then the default data will be generated from `src/main/resources/db/script/default-currency.sql` along with the initialization.

# Test

Run ```./mvnw test```

Test cases 1 - 6 are located in `src/test/java/com/example/coin`

# Lint

Run ```./mvnw spotless:apply```