package ru.priamosudov.hotelme.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.priamosudov.hotelme.user.exception.UserNotFoundException;
import ru.priamosudov.hotelme.user.domain.User;
import ru.priamosudov.hotelme.user.repository.UserRepository;
import ru.priamosudov.hotelme.user.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        log.debug("try to find user with [{}] username", username);
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
