package ru.priamosudov.hotelme.user.repository;

import org.junit.jupiter.api.BeforeEach;
import ru.priamosudov.hotelme.repository.BaseRepositoryTest;
import ru.priamosudov.hotelme.user.domain.BaseUser;
import ru.priamosudov.hotelme.user.domain.SecuredUser;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BaseUserRepositoryTest extends BaseRepositoryTest {

    protected static final String COMMON_USER_USERNAME = "Alice";
    protected static final String COMMON_USER_FIRST_NAME = "ABC";
    protected static final String COMMON_USER_LAST_NAME = "XYZ";
    protected static final String COMMON_USER_PASSWORD = "password$$$";
    protected static final Date COMMON_USER_BIRTH_DATE = new Date(123);

    @BeforeEach
    public void setUp() {
        addEntity(createUser(COMMON_USER_USERNAME));
        addEntity(createUser("Bob"));
    }

    protected void checkCommonUserData(BaseUser actualUser, String username) {
        assertEquals("User name is not matching", username, actualUser.getUsername());
        assertEquals("User first name is not matching", COMMON_USER_FIRST_NAME, actualUser.getFirstName());
        assertEquals("User last name is not matching", COMMON_USER_LAST_NAME, actualUser.getLastName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        assertEquals("User birth date is not matching",
                dateFormat.format(COMMON_USER_BIRTH_DATE), dateFormat.format(actualUser.getBirthDate()));
    }

    protected SecuredUser createUser(String username) {
        SecuredUser user = new SecuredUser();
        user.setUsername(username);
        user.setFirstName(COMMON_USER_FIRST_NAME);
        user.setLastName(COMMON_USER_LAST_NAME);
        user.setBirthDate(COMMON_USER_BIRTH_DATE);
        user.setPassword(COMMON_USER_PASSWORD);

        return user;
    }
}
