package ru.priamosudov.hotelme.user.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.priamosudov.hotelme.user.dto.UserDto;

import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecuredUserDto extends UserDto {

    @Size(min = 6, max = 50)
    private String password;
}
