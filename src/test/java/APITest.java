import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class APITest {

    private String baseUri = "https://dummyjson.com/";
  private String userData;
    private String ID;
    String userDataFilePath = "src/test/resources/Data.json";


    @DataProvider(name = "GETreqData")
    public static Object[][] dataProvider() {
        return new Object[][]{
                {"1","Emily","Johnson",28},
                {"2","Michael","Williams",35},
                {"3","Sophia","Brown",42}
        };
    }


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


        // Assertions
        Assert.assertEquals(response.statusCode(), 201);
        response.then().assertThat().body("firstName", equalTo("ali"));
        response.then().assertThat().body("lastName", equalTo("khan"));
        response.then().assertThat().body("age", equalTo("31"));



    }

    @Test(dataProvider = "GETreqData")
    public void getRequest(String ID,String firstName,String lastName,Integer age)  {

        Response response = given()
                .when()
                .get("users/"+ID);



        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body("firstName", equalTo(firstName));
        response.then().assertThat().body("lastName", equalTo(lastName));
        response.then().assertThat().body("age", equalTo(age));

    }

    @Test
    public void UpdateUserUsingPUT()  {
        System.out.println();

        Response response = given()
                .when()
                .contentType("application/json")
                .body("{\"firstName\":\"Hadi\"}")
                .put("users/1");

        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body("firstName", equalTo("Hadi"));

    }
    @Test
    public void updateUserUsingPATCH()  {

//        Map<String, String> userData = new HashMap<>();
//        userData.put("firstName", "Hadi");

        Response response = given()
                .when()
                .contentType("application/json")
               // .body(userData)
                .body("{\"firstName\":\"Hadi\"}")
                .patch("users/1");

        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body("firstName", equalTo("Hadi"));

    }
    @Test
    public void DeleteUser()  {


        Response response = given()
                .when()
                .contentType("application/json")
                .delete("users/1");

        Assert.assertEquals(response.statusCode(), 200);
        boolean isDeleted = response.jsonPath().getBoolean("isDeleted");
        Assert.assertTrue(isDeleted, "Expected isDeleted to be true");


    }


}
