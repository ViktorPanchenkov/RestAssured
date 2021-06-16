package utils;

import lombok.Data;
import pojos.CreateUserRequest;


@Data
public class UserGenerator {

    public static CreateUserRequest getSimpleUser(){
        return CreateUserRequest.builder()
                .name("Kolia")
                .job("QA Automation")
                .build();
    }
}
