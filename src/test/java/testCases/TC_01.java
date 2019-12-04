package testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.PrintStream;

public class TC_01 {
    boolean isPresent;
    @Test
    void getUsername()
    {
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/users");
        String responseBody = response.getBody().asString();
        //System.out.println("Response Body is:" + responseBody);
        Assert.assertEquals(responseBody.contains("Samantha"),true);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        JsonPath jsonpath = response.jsonPath();
        var usernames = jsonpath.get("username");
        int i;
        int n = usernames.length;
        for(i=0; i<n; i++){
                if(usernames[i]=="Samantha"){
                    System.out.println(i);
            }
        }
        //System.out.println(usernames);


//        JsonPath path = response.jsonPath();
//
//        List<HashMap<String, Object>> data = path.getList("username");
//        for (HashMap<String, Object> singleObject : data) {
//            if (singleObject.get("id").equals(3)){
///               System.out.println(singleObject.get("username"));
//            }
//        }
    }
}