package testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TC_01 {

    //JSONArray array = new JSONArray();
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

    }

    @Test(priority = 1)
    void getUsername()
    {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/users");
        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(responseBody.contains("Samantha"));
        JSONArray users = new JSONArray(responseBody);
        int id = 0;
        for(int i = 0; i<users.length(); i++){
            JSONObject object = users.getJSONObject(i);
            if("Samantha".equalsIgnoreCase(object.getString("username"))){
                id = object.getInt("id");
                System.out.println("username Samantha found with ID: "+id);
            }
        }




/////////////////////////////////////////////////////////////////////////
//        var usernames = jsonpath.get("username");
//        int i;
//        int n = usernames.length;
//        for(i=0; i<n; i++){
//                if(jsonpath.[i]=="Samantha"){
//                    System.out.println(i);
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
//    }
//}