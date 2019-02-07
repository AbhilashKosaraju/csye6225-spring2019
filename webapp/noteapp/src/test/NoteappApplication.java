package csye6225.cloud.noteapp;

import io.resassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteApplicationTests {

    @Test
    public void testForUnauthGet() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.given().auth().premptive().basic(username "", password "")
                .when()
                .get(path:"/")
                .then()
                .assertThat()
                .statusCode(401);
    }


}