import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class APITest {

    private String baseUri = "https://dummyjson.com/";
  private String userData;
    private String ID;
    String userDataFilePath = "src/test/resources/Data.json";




    @BeforeClass
    public void setUp() throws IOException {
        RestAssured.baseURI = baseUri;
        // Read the JSON file as a String
        userData = Files.readString(Paths.get(userDataFilePath));


    }
    @Test
    public void UserPOSTrequest()  {

        // Send a POST request with the request body from the file
        Response response = given()
                .contentType("application/json")
                .body(userData)
                .when()
                .post("users/add");

        // Assert the status code
        Assert.assertEquals(response.statusCode(), 201);

                //.getBody().asString()); //
        // Print the response body
        System.out.println(response.getBody().asString());
        ID = response.jsonPath().getString("id");
        System.out.println(ID);
    }

    @Test
    public void getRequest()  {

        Response response = given()
                .when()
                .get("users/1");

            //  .get("/users/"+ID);
            // We could use "/users/" + ID to get the user we created earlier in the test.
        //However, since this is a dummy API with preset data, we're retrieving an existing user with a hardcoded ID (1).


        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void UpdateUserUsingPUT()  {
        System.out.println();

        Response response = given()
                .when()
                .contentType("application/json")
                .body(userData)
                .put("users/1");

        Assert.assertEquals(response.statusCode(), 200);


    }
    @Test
    public void updateUserUsingPATCH()  {
        System.out.println();

        Response response = given()
                .when()
                .contentType("application/json")
               .body(userData)
                .patch("users/1");

        Assert.assertEquals(response.statusCode(), 200);


    }
    @Test
    public void DeleteUser()  {
        System.out.println();

        Response response = given()
                .when()
                .contentType("application/json")
                .delete("users/1");

        Assert.assertEquals(response.statusCode(), 200);
        boolean isDeleted = response.jsonPath().getBoolean("isDeleted");
        Assert.assertTrue(isDeleted, "Expected isDeleted to be true");


    }


}
