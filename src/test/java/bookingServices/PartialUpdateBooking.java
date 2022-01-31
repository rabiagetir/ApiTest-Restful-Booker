package bookingServices;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.PartialUpdateRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PartialUpdateBooking {

    public String createToken ()
    {
        String postData = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        RequestSpecification requestSpecification = given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(postData);
        Response response = requestSpecification.post("https://restful-booker.herokuapp.com/auth");

        JsonPath jsonPathEvaluator = response.jsonPath();
        String token = jsonPathEvaluator.get("token");
        System.out.println(token);
        return token;


    }
    public int createBooking ()
    {
        String postdata = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        RequestSpecification requestSpecification = given().contentType(ContentType.JSON)
                .body(postdata)
                .log().all();
        Response response = requestSpecification.post("https://restful-booker.herokuapp.com/booking");
        JsonPath jsonPathEvaluator = response.jsonPath();
        int bookingId = jsonPathEvaluator.get("bookingid");
        return bookingId;

    }

    @Test
    public void PartialUpdate ()
    {
        PartialUpdateRequest partialUpdateRequest = new PartialUpdateRequest("James", "Brown");

        String requestBody = new Gson().toJson(partialUpdateRequest);
        given()
                .log().all().header("Content-Type", "application/json").cookie("token", createToken()).body(requestBody).
                when()
                .patch("https://restful-booker.herokuapp.com/booking/"+createBooking()).
                then()
                .statusCode(200)
                .log().all();

    }

}
