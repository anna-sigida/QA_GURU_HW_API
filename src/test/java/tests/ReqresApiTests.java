package tests;

import models.LoginBodyModel;
import models.LoginResponseModel;
import models.UserBodyModel;
import models.UserResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpec.*;
import static specs.UserSpec.*;

@Tag("API")
public class ReqresApiTests {
    LoginBodyModel authData = new LoginBodyModel();
    UserBodyModel userData = new UserBodyModel();

    @BeforeAll
    public static void setUp() {
        baseURI = "https://reqres.in/api";
    }

    @Test
    void createUserTest() {
        userData.setName("ivan");
        userData.setJob("chef");
        UserResponseModel response = step("Make request", () ->
                given(createRequestSpec)
                .when()
                .body(userData)
                .post()

                .then()
                .spec(createUserResponseSpec)
                .extract().as(UserResponseModel.class));
        step("Check result", () ->
                assertThat(response.getId()).isNotEmpty());
    }

    @Test
    void updateUserTest() {
        userData.setName("ivan");
        userData.setJob("chef");

        UserResponseModel response = step("Make request", () ->
                given(updateRequestSpec)
                .when()
                .body(userData)
                .patch()

                .then()
                .spec(updateUserResponseSpec)
                .extract().as(UserResponseModel.class));

        step("Check result", () ->
                assertThat(response.getUpdatedAt()).startsWith(String.valueOf(LocalDate.now())));
    }

    @Test
    void deleteUserTest() {
        Integer response = step("Make request", () -> given()
                .filter(withCustomTemplates())
                .when()
                .delete("/users/2")
                .then()
                .extract().statusCode());

        step("Check result", () -> assertThat(response).isEqualTo(204));
    }

    @Test
    public void successfulLoginTest() {

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Make request", () ->
                given(loginRequestSpec)
                .when()
                .body(authData)
                .post()

                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseModel.class));

        step("Check result", () ->
                assertThat(response.getToken()).isAlphanumeric());
    }

    @Test
    public void unsuccessfulLoginTest() {

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("");

        LoginResponseModel response = step("Make request", () ->
                given(loginRequestSpec)
                .when()
                .body(authData)
                .post()

                .then()
                .spec(missingPassResponseSpec)
                .extract().as(LoginResponseModel.class));

        step("Check result", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }
}
