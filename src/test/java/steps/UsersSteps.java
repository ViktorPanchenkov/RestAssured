package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojos.CreateUserRequest;
import pojos.CreateUserResponce;
import pojos.FullPojoUser;
import static io.restassured.RestAssured.given;

import java.util.List;

public class UsersSteps {
    private static final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api")
                    .setBasePath("/users")
                    .setContentType(ContentType.JSON)
                    .build();

    public static List<FullPojoUser> getUsers(){
        return given()
                .spec(REQ_SPEC)
                .get()
                .jsonPath().getList("data",FullPojoUser.class);
    }


    private CreateUserResponce user;

    public CreateUserResponce createUser(CreateUserRequest request){
        user = given().body(request).post().as(CreateUserResponce.class);
        return user;
    }

    public FullPojoUser getUser(){
        return given().given().get("/" + user.getId()).as(FullPojoUser.class);
    }

}
