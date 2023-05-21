package com.gini;

import com.gini.dto.request.user.CreateUserRequest;
import com.gini.dto.request.user.LoginUserRequest;
import com.gini.dto.response.user.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * @SpringBootTest -> porneste testul si incarca spring application context cu toate beanurile etc.
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT -> by default @SpringBootTest nu porneste
 * si web serverul asa ca aceasta linie de cod porneste serverul pe un random port: Ex: Tomcat started on port 10000
 * <p>
 * MOCK (Default) -> daca nu definim atributul 'webEnvironment ' atunci adnotarea va incarca
 * Application Context cu un mock web environment.
 * <p>
 * RANDOM_PORT -> incarca un WebServerApplicationContext si un web environment real -> acesta se
 * foloseste la integration test.
 * <p>
 * DEFINED_PORT -> la fel ca RANDOM_PORT doar ca trebuie sa definim un port
 * @LocalServerPort -> asigneaza acel random port unei variabile pe care o vom folosi in TestRestTemplate pentru a
 * apela API-ul aplicatie si implicit pentru a rula testul de integrare
 */


@SpringBootTest(classes = {IntegrationTestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends MySqlTestContainer {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testRestTemplate;

//
//    @Test
//    void xxx() {
//        System.out.println("database: " + MY_SQL_CONTAINER.getDatabaseName() +
//                " username: " + MY_SQL_CONTAINER.getUsername() +
//                " password: " + MY_SQL_CONTAINER.getPassword() +
//                "URL:++++ " + MY_SQL_CONTAINER.getJdbcUrl()
//
//        );

//        assertTrue(MY_SQL_CONTAINER.isRunning());
//    }

    @Test
    @DisplayName("""
            call /login endpoint and verify if the user exists in database so
            he can logg in the application
            """)
    void loginUserFlow() {
        //given
        createUserInDatabaseForTest();

        String loginEndpoint = "http://localhost:" + port + "/v1/user/login";
        LoginUserRequest loginRequest = new LoginUserRequest("test@gamil.com", "test123");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<LoginUserRequest> request = new HttpEntity<>(loginRequest, httpHeaders);

        //when
        ResponseEntity<UserResponse> actual = testRestTemplate.postForEntity(loginEndpoint, request, UserResponse.class);

        //then
        assertEquals(200, actual.getStatusCode().value());

    }

    private void createUserInDatabaseForTest() {
        String createUserEndpoint = "http://localhost:" + port + "/v1/user";
        CreateUserRequest userRequest = new CreateUserRequest("testtest", "test@gamil.com", "test123");

        HttpHeaders httpHeaders2 = new HttpHeaders();
        httpHeaders2.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<CreateUserRequest> request2 = new HttpEntity<>(userRequest, httpHeaders2);

        ResponseEntity<UserResponse> actual2 = testRestTemplate.postForEntity(createUserEndpoint, request2, UserResponse.class);

        System.out.println("+++++ " + actual2);
    }

}
