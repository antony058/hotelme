package ru.priamosudov.hotelme.user.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.priamosudov.hotelme.user.domain.SecuredUser;
import ru.priamosudov.hotelme.user.exception.UserAlreadyExistException;
import ru.priamosudov.hotelme.user.exception.UserNotFoundException;
import ru.priamosudov.hotelme.user.repository.SecuredUserRepository;
import ru.priamosudov.hotelme.user.service.impl.SecuredUserServiceImpl;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class SecuredUserServiceTest {

    private static final String COMMON_USER_USERNAME = "Alice";

    private SecuredUserService securedUserService;
    private SecuredUserRepository userRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        userRepository = mock(SecuredUserRepository.class);
        securedUserService = new SecuredUserServiceImpl(userRepository);
    }

    @After
    public void kickOff() {
        expectedException = ExpectedException.none();
    }

    @Test
    public void whenFindUserByNullableUsername_thenThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Username must not be null");

        securedUserService.getSecuredUserByUsername(null);
    }

    @Test
    public void whenFindUserByEmptyUsername_thenThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Username must not be null");

        securedUserService.getSecuredUserByUsername("");
    }

    @Test
    public void whenFindUserByNotExistingUsername_thenThrowUserNotFoundException() {
        expectedException.expect(UserNotFoundException.class);
        expectedException.expectMessage("User was not found.");

        try {
            securedUserService.getSecuredUserByUsername(COMMON_USER_USERNAME);
        } catch (UserNotFoundException ex) {
            assertEquals("Username doesn't match", COMMON_USER_USERNAME, ex.getUsername());
            throw ex;
        }
    }

    @Test
    public void whenFindUserByExistingUsername_thenReturnUser() {
        SecuredUser user = new SecuredUser();
        user.setUsername(COMMON_USER_USERNAME);
        when(userRepository.findByUsername(COMMON_USER_USERNAME)).thenReturn(Optional.of(user));

        securedUserService.getSecuredUserByUsername(COMMON_USER_USERNAME);

        verify(userRepository).findByUsername(COMMON_USER_USERNAME);
        assertNotNull("User is null", user);
        assertEquals("Username doesn't match", COMMON_USER_USERNAME, user.getUsername());
    }

    @Test
    public void whenAddExistingUser_thenThrowUserAlreadyExistException() {
        expectedException.expect(UserAlreadyExistException.class);
        expectedException.expectMessage("User already exist.");

        SecuredUser user = new SecuredUser();
        user.setUsername(COMMON_USER_USERNAME);
        when(userRepository.findByUsername(COMMON_USER_USERNAME)).thenReturn(Optional.of(user));

        try {
            securedUserService.addUser(user);
        } catch (UserAlreadyExistException ex) {
            verify(userRepository).findByUsername(COMMON_USER_USERNAME);
            assertEquals("Username doesn't match", COMMON_USER_USERNAME, user.getUsername());
            throw ex;
        }
    }

    @Test
    public void whenAddCorrectUser_thenSuccessfullyReturn() {
        when(userRepository.findByUsername(COMMON_USER_USERNAME)).thenReturn(Optional.empty());
        SecuredUser user = new SecuredUser();
        user.setUsername(COMMON_USER_USERNAME);

        securedUserService.addUser(user);

        verify(userRepository).findByUsername(COMMON_USER_USERNAME);
        verify(userRepository).save(user);
    }
}
