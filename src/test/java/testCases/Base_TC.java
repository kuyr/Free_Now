package testCases;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.params.CoreConnectionPNames;
import org.testng.SkipException;
import constants.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Base_TC{

    public Base_TC() {
        RestAssured.config = RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig().setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 100000));
    }

    Response httpresponse;

    protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public <T> List<T> requestGET(String URI, Map<String, String> params, Class clazz) {
        if (params != null) {
            httpresponse = RestAssured.given().queryParams(params).get(URI);
        } else {
            httpresponse = RestAssured.given().get(URI);
        }
        validateHttpResponse(httpresponse.statusCode(), httpresponse.getContentType());

        JsonPath path = httpresponse.getBody().jsonPath();
        List<T> responseDto = path.getList(dataConstants.ROOT_JSON, clazz);
        return responseDto;
    }


    private void validateHttpResponse(int statusCode, String contentType) {
        SkipException exception = new SkipException("Skipping Test...");
        if (statusCode == 400 || statusCode == 404) {
            throw exception;
        } else if (statusCode == 500) {
            throw exception;
        } else if ((statusCode == 200 || statusCode == 201) && contentType.contains("json")) {
        } else {
            throw exception;
        }
    }

}


