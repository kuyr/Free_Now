package testCases;

import base.TestBase;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class TC_02 extends TestBase {
    RequestSpecification httpRequest;
    Response response;

    @BeforeClass
    void getPosts(){
        logger.info("_____________initializing TC_02 Getting Samanthas Posts______________")
    }
}
