package bookingServices;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateBooking {
    @BeforeClass
    public void createToken ()
    {
        {
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
    @Test
    public void createBooking ()
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

        given()
                .contentType(ContentType.JSON)
                .body(postdata)
                .log().all().
                when()
                .post("https://restful-booker.herokuapp.com/booking").
                then()
                .statusCode(200)
                .log().all();
    }


}
