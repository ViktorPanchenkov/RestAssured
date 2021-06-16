import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.CreateUserRequest;
import pojos.CreateUserResponce;
import pojos.FullPojoUser;
import pojos.UserPojo;
import steps.UsersSteps;
import utils.RestWrapper;
import utils.UserGenerator;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RestTest {

    private static RestWrapper api;

    private static void Login(){
        api = RestWrapper.loginAs("eve.holt@reqres.in","cityslicka");
    }


    private static final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api")
            .setBasePath("/users")
            .setContentType(ContentType.JSON)
            .build();
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
                .spec(REQ_SPEC)
                .when().get()
                .then().log().body().
                body("data.find{it.email=='george.bluth@reqres.in'}.first_name",equalTo("George"));
    }
    @Test
    public void GetEmails(){

       List<String> emails = given()
                .spec(REQ_SPEC)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("data.email");

    }

    @Test
    public void getUsers(){
        List<FullPojoUser> users = given()
                .spec(REQ_SPEC)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("data", FullPojoUser.class);

        assertThat(users).extracting(FullPojoUser::getEmail).contains("george.bluth@reqres.in");
    }
    @Test
    public void GetUsersUsingSteps(){
    List<FullPojoUser> users = UsersSteps.getUsers();
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
               .spec(REQ_SPEC)
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
    @Test
    public void CreateUserBuilder(){
        CreateUserRequest request = CreateUserRequest.builder().
                name("Ivan")
                .job("QA")
                .build();

        CreateUserResponce responce =
                given()
                        .spec(REQ_SPEC)
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

    @Test
    public void CreateUserWithUserGenerator(){
        CreateUserRequest request = UserGenerator.getSimpleUser();

        UsersSteps userApi = new UsersSteps();
        CreateUserResponce responce =
                given()
                .spec(REQ_SPEC)
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

    @Test
    public void ReplaceString(){
     String str1 = new String("Hot Java");
     String str2 = "Java2";
     String [] strings = str1.split(" ");
     System.out.println(str1.hashCode());

     for(String element:strings){
         System.out.println(element);
        }
    }





}
