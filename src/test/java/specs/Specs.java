package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.BODY;

public class Specs {
    public static RequestSpecification loginRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().all();

    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification missingPassResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(ALL)
            .build();

    public static RequestSpecification createRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().all();

    public static RequestSpecification updateRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().all();

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

}
