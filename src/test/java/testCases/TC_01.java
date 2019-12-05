package testCases;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC_01 extends TestBase {

    @BeforeClass
    void getAllUsers() {
        logger.info("_____________initializing TC_01 Getting All Users______________");
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/users");

    }

    @Test
    void checkStatusCode() {
        logger.info("_________________checking status code_________________________");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    void getUsername() {
        logger.info("_______________searching for username Samantha__________________");
        String responseBody = response.getBody().asString();
        logger.info(String.format("response body --> %s", responseBody));
        Assert.assertTrue(responseBody.contains("Samantha"));

        JSONArray users = new JSONArray(responseBody);
        int id = 0;
        for (int i = 0; i < users.length(); i++) {
            JSONObject object = users.getJSONObject(i);
            if ("Samantha".equalsIgnoreCase(object.getString("username"))) {
                id = object.getInt("id");
                System.out.println("username Samantha found with ID: " + id);
            }
            logger.info("username Samantha found with ID: " + id);
        }
    }

    @Test
    void checkResponseTime() {
        logger.info("_____________________checking response time____________________");
        long responseTime = response.getTime();
        logger.info(String.format("response time is --> %d", responseTime));
        if (responseTime > 15000)
            logger.warn("Response time is greater than 15000");
        Assert.assertTrue(responseTime < 15000);
    }

    @AfterClass
    void tearDown() {
        logger.info("________________first test case passed, WE MOVE!!!_____________");
    }


//        System.out.println(id);
//        response = httpRequest.request(Method.GET,"/posts");
//        responseBody = response.getBody().asString();
//        JSONArray posts = new JSONArray(responseBody);
//        List<Integer> samanthaPosts = new ArrayList<>();
//        for(int i=0; i<posts.length(); i++){
//            JSONObject post = posts.getJSONObject(i);
//            if(post.getInt("userId") == id){
//                samanthaPosts.add(post.getInt("id"));
//            }
//        }
//
//        response = httpRequest.request(Method.GET, "/comments");
//        responseBody = response.getBody().asString();
//        JSONArray comments = new JSONArray(responseBody);
//        for(int i=0; i<comments.length(); i++){
//            JSONObject comment = comments.getJSONObject(i);
//            if(samanthaPosts.contains(comment.getInt("postId"))){
//                System.out.println(comment.getInt("postId"));
//                System.out.println(comment.getString("email"));
//            }
//        }

/////////////////////////////////////////////////////////////////////////
//        var usernames = jsonpath.get("username");
//        int i;
//        int n = usernames.length;
//        for(i=0; i<n; i++){
//                if(jsonpath.[i]=="Samantha"){
//                    System.out.println(i);
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