package ru.priamosudov.hotelme.user.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.priamosudov.hotelme.user.domain.User;
import ru.priamosudov.hotelme.user.exception.UserNotFoundException;
import ru.priamosudov.hotelme.user.service.SecuredUserService;
import ru.priamosudov.hotelme.user.service.UserService;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest {

    private static final String ROOT_PATH = "/user";
    private static final String COMMON_USER_USERNAME = "Alice";
    private static final String COMMON_USER_FIRST_NAME = "ABC";
    private static final String COMMON_USER_LAST_NAME = "XYZ";
    private static final Date COMMON_USER_BIRTH_DATE = new Date(123);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecuredUserService securedUserService;

    @MockBean
    private UserService userService;

    @MockBean(name = "userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    public void whenGetUserByNotExistingUsername_thenReturnBadRequestResponseStatus() throws Exception {
        when(userService.getUserByUsername(COMMON_USER_USERNAME)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get(ROOT_PATH + "/" + COMMON_USER_USERNAME))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)));
    }

    @Test
    @WithMockUser
    public void whenGetExistingUserByUsername_thenSuccessfullyReturnHim() throws Exception {
        when(userService.getUserByUsername(COMMON_USER_USERNAME)).thenReturn(getDefaultUser());

        mockMvc.perform(get(ROOT_PATH + "/" + COMMON_USER_USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.username", is(COMMON_USER_USERNAME)))
                .andExpect(jsonPath("$.firstName", is(COMMON_USER_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(COMMON_USER_LAST_NAME)))
                .andExpect(jsonPath("$.birthDate", is("1970-01-01")));
    }

    private User getDefaultUser() {
        User user = new User();
        user.setUsername(COMMON_USER_USERNAME);
        user.setFirstName(COMMON_USER_FIRST_NAME);
        user.setLastName(COMMON_USER_LAST_NAME);
        user.setBirthDate(COMMON_USER_BIRTH_DATE);

        return user;
    }

    @TestConfiguration
    static class Conf {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }
}
