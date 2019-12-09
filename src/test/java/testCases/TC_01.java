package testCases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.restassured.RestAssured;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.*;
import DataTransferObject.*;
import org.testng.collections.CollectionUtils;

import static org.testng.collections.CollectionUtils.*;


public class TC_01 extends Base_TC {

    private usernameSearchResponseDTO user;
    private List<userPostResponseDTO> postList;
    private Map<String, String> param;

    public static String URI;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = dataConstants.BASE_URI;
    }


    @Test(priority = 1)
    public void searchTheUser() {
        URI = "users";
        List<usernameSearchResponseDTO> userList = this.requestGET(URI, null, usernameSearchResponseDTO.class);
        try {
            if (userList != null) {
                user = userList.stream().filter(k -> k.getUsername().equals(dataConstants.USERNAME)).findFirst().get();
                LOGGER.info(String.format("Username %s has ID %d", dataConstants.USERNAME, user.getId()));
            } else {
                LOGGER.info("User List received is empty");
            }
        } catch (NoSuchElementException e) {
            LOGGER.info(String.format("Username %s is not present in Http response", dataConstants.USERNAME));
        }
    }

    @Test(priority = 2)
    public void searchPosts() {
        this.searchTheUser();
        URI = "posts";
        param = new HashMap<String, String>();

        if (user != null) {
            param.put(dataConstants.PARAM_USER_ID, String.valueOf(user.getId()));

            postList = this.requestGET(URI, param, usernameSearchResponseDTO.class);
            if (hasElements(postList)) {
                LOGGER.info(String.format("Username %s has  %d posts", dataConstants.USERNAME, postList.size()));
            } else {
                LOGGER.warning(String.format("Username %s doesn't have any posts", dataConstants.USERNAME));
            }
        } else {
            LOGGER.info(String.format("Username %s is not present in Http response", dataConstants.USERNAME));
        }
    }

    @Test(priority = 3)
    public void fetchCommentAndvalidateEmail() {
        searchTheUser();
        searchPosts();
        URI = "comments";
        param = new HashMap<String, String>();
        if (postList != null) {
            postList.forEach(k -> {
                param.put(dataConstants.PARAM_POST_ID, String.valueOf(k.getId()));
                List<userPostCommentResponseDTO> commentList = this.requestGET(URI, param, userPostCommentResponseDTO.class);

                if (commentList != null && CollectionUtils.hasElements(commentList)) {
                    List<String> emailList = commentList.stream().map(userPostCommentResponseDTO::getEmail)
                            .collect(Collectors.toList());
                    this.validateEmailPattern(emailList);
                    LOGGER.info(String.format("PostID %s with comments size %d has valid emails", k.getId(),
                            commentList.size()));
                } else {
                    LOGGER.warning(String.format("PostID %s doesn't have any comments", k.getId()));
                }
            });
        }else {
            LOGGER.warning(String.format("Username %s doesn't have any posts", dataConstants.USERNAME));
        }
    }

    // validate email using RegEx.
    protected void validateEmailPattern(List<String> email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(dataConstants.EMAIL_REGEX,
                Pattern.CASE_INSENSITIVE);
        if (email != null) {
            email.forEach(k -> {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(k);
                Assert.assertTrue(matcher.find(), "Email syntax is not valid");
            });
        }else {
            LOGGER.info("Email List received is empty");
        }
    }

}