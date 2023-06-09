import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NotMineBlogTests extends AbstractClass {

    @BeforeAll
    static void setUp() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", getLogin());
        requestBody.put("password", getPassword());
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());
        String token = request.post(getBaseUrl() + "api/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token")
                .toString();
        setToken(token);
    }

    @Test
    @Order(1)
    void getPublicationsPositiveTest() {
        given()
                .header(xAuthToken, getToken())
                .queryParam("owner", "notMe")
                .queryParam("order", "ASC")
                .queryParam("sort", "createdAt")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl() + "api/posts")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void getInvalidOwner() {
        int statusCode = given()
                .header(xAuthToken, getToken())
                .queryParam("owner", "gerrHerr")
                .queryParam("order", "ASC")
                .queryParam("sort", "createdAt")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl() + "api/posts")
                .statusCode();
        assertThat("Expected, that this owner does not exist", statusCode, equalTo(400));

    }


    @Test
    @Order(3)
    void getUnauthorizedUserTest() {
        given()
                .queryParam("owner", "notMe")
                .queryParam("order", "ASC")
                .queryParam("sort", "createdAt")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl() + "api/posts")
                .then()
                .statusCode(401);
    }


    @Test
    @Order(4)
    void getUnexistablePageTest() {
        int statusCode = given()
                .header(xAuthToken, getToken())
                .queryParam("owner", "notMe")
                .queryParam("order", "ASC")
                .queryParam("sort", "createdAt")
                .queryParam("page", "-5")
                .when()
                .get(getBaseUrl() + "api/posts")
                .statusCode();

        assertThat("Expected, that this page does not exist", statusCode, equalTo(400));
    }

    @Test
    @Order(5)
    void getOrderDESCTest() {
        given()
                .header(xAuthToken, getToken())
                .queryParam("owner", "notMe")
                .queryParam("order", "DESC")
                .queryParam("sort", "createdAt")
                .queryParam("page", "5")
                .when()
                .get(getBaseUrl() + "api/posts")
                .then()
                .statusCode(200);
    }

}