import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.CreateUserRequest;
import pojos.CreateUserResponce;
import pojos.FullPojoUser;
import pojos.UserPojo;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RestTest {


    @Test
    public void GetUsers(){
         given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().log().body().
                 body("data[0].email",equalTo("george.bluth@reqres.in"));

    }

    @Test
    public void GetUsersLambda(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().log().body().
                body("data.find{it.email=='george.bluth@reqres.in'}.first_name",equalTo("George"));
    }
    @Test
    public void GetEmails(){

       List<String> emails = given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("data.email");

    }

    @Test
    public void getUsers(){
        List<FullPojoUser> users = given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("data", FullPojoUser.class);

        assertThat(users).extracting(FullPojoUser::getEmail).contains("george.bluth@reqres.in");
    }

    @Test
    public void Qqual(){
        UserPojo user1 = new UserPojo();
        user1.setEmail("vdv@gmail.com");
        user1.setId(2);
        UserPojo user2 = new UserPojo();
        user2.setEmail("vdv@gmail.com");
        user2.setId(2);

        System.out.println(user1.equals(user1));

        boolean ISHashCodeEquals = user1.hashCode() == user2.hashCode();
        System.out.println(ISHashCodeEquals);
    }

    @Test
    public void CreateUser(){
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Kolia");
        request.setJob("automation");

        CreateUserResponce responce =
                given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                        .log().body()
                .extract().as(CreateUserResponce.class);
        System.out.println(request.getName());
        System.out.println(responce.getName());

        assertThat(responce)
                .isNotNull()
                .extracting(CreateUserResponce::getName)
                .isEqualTo(request.getName());

    }





}
