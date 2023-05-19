package com.gini;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


/**
 * @Testcontainers -> scaneaza clasa dupa @Container ca sa porneasca containerele.
 * <p>
 * public static final MySQLContainer<?> MY_SQL_CONTAINER -> static insemana ca porneste un singur container pentru toate testele.
 * Daca vrem sa se porneasca cate un container pentru fiecare metoda de test vom sterge -> 'static' din fata containerului.
 */


@Testcontainers
class MySqlTestContainer {

    @Container
    public static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("pictures_db")
            .withUsername("root")
            .withPassword("123");

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        //   registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        //   registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
}
