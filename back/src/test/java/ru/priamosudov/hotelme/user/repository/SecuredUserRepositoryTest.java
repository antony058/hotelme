package ru.priamosudov.hotelme.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.priamosudov.hotelme.user.domain.BaseUser;
import ru.priamosudov.hotelme.user.domain.SecuredUser;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

class SecuredUserRepositoryTest extends BaseUserRepositoryTest {

    @Autowired
    private SecuredUserRepository securedUserRepository;

    @Test
    public void shouldNotFoundUserByUsername() {
        Optional<SecuredUser> userOptional = securedUserRepository.findByUsername("_" + COMMON_USER_USERNAME);

        assertFalse("User was founded", userOptional.isPresent());
    }

    @Test
    public void shouldSuccessfullyFoundUserByUsername() {
        Optional<SecuredUser> userOptional = securedUserRepository.findByUsername(COMMON_USER_USERNAME);

        assertTrue("User was not found", userOptional.isPresent());
        SecuredUser securedUser = userOptional.get();
        checkCommonUserData(securedUser, COMMON_USER_USERNAME);
    }

    private void checkCommonUserData(SecuredUser actualUser, String username) {
        super.checkCommonUserData(actualUser, username);
        assertEquals("User password is not matching", COMMON_USER_PASSWORD, actualUser.getPassword());
    }
}
