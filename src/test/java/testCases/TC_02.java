package testCases;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class TC_02 extends TestBase {
    RequestSpecification httpRequest;
    Response response;

    @BeforeClass
    void getPosts(){
        logger.info("_____________initializing TC_02 Getting Samanthas Posts______________");
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/posts");
    }

//     System.out.println(id);
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
}
