package pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserResponce {

    private String name;
    private String job;
    private int id;
    private String createdAt;
}
