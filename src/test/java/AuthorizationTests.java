import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


public class AuthorizationTests extends AbstractClass {

    @Test
    @Order(1)
    void validLoginTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", getLogin());
        requestBody.put("password", getPassword());
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post(getBaseUrl() + "api/login");
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(statusCode, 200);
        System.out.println("The status code recieved: " + statusCode);
    }

    @Test
    @Order(2)
    void inValidLoginBodyTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", getLogin());
        requestBody.put("password", getPassword());
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post(getBaseUrl() + "api/login");
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(statusCode, 400);
        System.out.println("Invalid body (login): " + statusCode);
    }

    @Test
    @Order(3)
    void inValidPasswordTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", getLogin());
        requestBody.put("password", getPassword() + "123456");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post(getBaseUrl() + "api/login");
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(statusCode, 401);
        System.out.println("Invalid password: " + statusCode);
    }
}
