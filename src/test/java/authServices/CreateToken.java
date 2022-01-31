package authServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateToken {
    @Test
    public void createToken ()
    {
        /*Map<String ,Object> queryParams = new HashMap<>();
        queryParams.put("username", "admin");
        queryParams.put("password", "password123");*/
        String postData = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(postData).
                when()
                .post("https://restful-booker.herokuapp.com/auth").
                then().
                statusCode(200).log().all();
    }
}
