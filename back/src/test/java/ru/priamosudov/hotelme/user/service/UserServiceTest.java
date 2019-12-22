package ru.priamosudov.hotelme.user.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.priamosudov.hotelme.user.exception.UserNotFoundException;
import ru.priamosudov.hotelme.user.domain.User;
import ru.priamosudov.hotelme.user.repository.UserRepository;
import ru.priamosudov.hotelme.user.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private static final String COMMON_USER_USERNAME = "Alice";

    private UserService userService;
    private UserRepository userRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @After
    public void kickOff() {
        expectedException = ExpectedException.none();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUsernameIsNull() {
        userService.getUserByUsername(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUsernameIsEmpty() {
        userService.getUserByUsername("");
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserWithRequestedUsernameIsNotExist() {
        expectedException.expect(UserNotFoundException.class);
        expectedException.expectMessage("User was not found.");

        try {
            userService.getUserByUsername(COMMON_USER_USERNAME);
        } catch (UserNotFoundException ex) {
            assertEquals("Username doesn't match", COMMON_USER_USERNAME, ex.getUsername());
            throw ex;
        }
    }

    @Test
    public void shouldSuccessfullyFoundUserByUsername() {
        User user = new User();
        user.setUsername(COMMON_USER_USERNAME);
        when(userRepository.findByUsername(COMMON_USER_USERNAME)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByUsername(COMMON_USER_USERNAME);

        assertNotNull("Founded user is nullable", actualUser);
        assertEquals("Actual user username are incorrect", COMMON_USER_USERNAME, actualUser.getUsername());
    }
}
