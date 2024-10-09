import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class ReqresApiTests {

    @BeforeAll
    public static void setUp() {
        baseURI = "https://reqres.in/api";
    }

    @Test
    void createUserTest() {
        String bodyUser = "{" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"" +
                "}";
        given()
                .when()
                .body(bodyUser)
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    void updateUserTest() {
        String bodyUser = "{" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"" +
                "}";

        given()
                .when()
                .body(bodyUser)
                .patch("/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("updatedAt", startsWith(String.valueOf(LocalDate.now())));
    }

    @Test
    void deleteUserTest() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    public void successfulLoginTest() {
        String body = "{" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post("/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
