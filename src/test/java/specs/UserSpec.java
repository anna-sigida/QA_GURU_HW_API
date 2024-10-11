package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;

public class UserSpec {
    public static RequestSpecification createRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .basePath("/users");

    public static RequestSpecification updateRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .basePath("/users/2");

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(BODY)
            .build();

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(BODY)
            .build();

}
