package testSuite;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TC_01 {
    @Test
    public void GET_Username()
    {
        given()
                .when()
                    .get("https://jsonplaceholder.typicode.com/users/3")
                .then()
                    .statusCode(200)
                    .statusLine("HTTP/1.1 200 OK")
                    .assertThat().body("username", equalTo("Samantha"));
    }

}
