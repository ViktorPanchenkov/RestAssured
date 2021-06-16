package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import pojos.CreateUserRequest;
import pojos.CreateUserResponce;
import pojos.FullPojoUser;
import pojos.UserLogin;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RestWrapper {

    private static final String BASE_URL = "https://reqres.in/api";
    private static  RequestSpecification REQ_SPEC;
    private Cookies cookies;

    private RestWrapper(Cookies cookies){
        this.cookies = cookies;

        REQ_SPEC = new RequestSpecBuilder()
                .addCookies(cookies)
                .setBaseUri(BASE_URL)
                .setBasePath("/users")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RestWrapper loginAs(String login, String password){
      Cookies cookies = given()
              .contentType(ContentType.JSON)
              .baseUri(BASE_URL)
              .basePath("/login")
              .body(new UserLogin(login,password))
              .post()
              .getDetailedCookies();

      return new RestWrapper(cookies);
    }
    private CreateUserResponce user;

    public CreateUserResponce createUser(CreateUserRequest request){
        user = given().body(request).post().as(CreateUserResponce.class);
        return user;
    }

    public static List<FullPojoUser> getUsers(){
        return given()
                .spec(REQ_SPEC)
                .get()
                .jsonPath().getList("data",FullPojoUser.class);
    }
}
