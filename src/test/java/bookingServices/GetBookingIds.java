package bookingServices;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class GetBookingIds {
    @BeforeClass
    public void createToken ()
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

    public void CreateBooking ()
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

    @Test
    public void getBookingIds ()
    {
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON).log().all();
        String baseUrl = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.get(baseUrl);
        getBookingIdsAttachments(requestSpecification, baseUrl,response);
        Assert.assertEquals(response.getStatusCode(),200);

    }

    public String getBookingIdsAttachments (RequestSpecification requestSpecification, String baseUrl, Response response)
    {
        String html = "Url"+ baseUrl + "\n\n" +
                "request header="+ ((RequestSpecificationImpl) requestSpecification).getHeaders() + "\n\n"+
                "request body="+ ((RequestSpecificationImpl) requestSpecification).getBody() + "\n\n"+
                "response body="+response.getBody().asString();
        Allure.addAttachment("Response of getBookingIds ", html);
        return html;

    }

}
