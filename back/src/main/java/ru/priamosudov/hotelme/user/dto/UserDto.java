package ru.priamosudov.hotelme.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserDto {

    @Size(min = 4, max = 50)
    private String username;

    @Size(min = 2, max = 90)
    private String firstName;

    @Size(min = 2, max = 90)
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
}
