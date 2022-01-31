package bookingServices;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetBooking {
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
    public int CreateBooking ()
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

        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .body(postdata)
                .log().all();
        Response response = requestSpecification.post("https://restful-booker.herokuapp.com/booking");
        JsonPath jsonPathEvaluator = response.jsonPath();
        int bookingId = jsonPathEvaluator.get("bookingid");
        return bookingId;

    }

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider ()
    {
        return new Object[][]{
                {CreateBooking (),200}
               //{88,404}

        };
    }

    @Test (dataProvider = "dataProvider")
    public void getBookingDetail (int id, int statusCode)
    {
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).log().all();
        String url = "https://restful-booker.herokuapp.com/booking/"+id;
        Response response = requestSpecification.get(url);
        getBookingIdAttachments (requestSpecification, url, response);
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    public String getBookingIdAttachments (RequestSpecification requestSpecification, String baseUrl, Response response)
    {
        String html = "Url"+ baseUrl + "\n\n" +
                "request header="+ ((RequestSpecificationImpl) requestSpecification).getHeaders() + "\n\n"+
                "request body="+ ((RequestSpecificationImpl) requestSpecification).getBody() + "\n\n"+
                "response body="+response.getBody().asString();
        Allure.addAttachment("Response of getBookingId ", html);
        return html;

    }

}
