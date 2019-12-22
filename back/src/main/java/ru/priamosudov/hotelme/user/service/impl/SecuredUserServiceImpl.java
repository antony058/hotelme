package ru.priamosudov.hotelme.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.priamosudov.hotelme.user.exception.UserAlreadyExistException;
import ru.priamosudov.hotelme.user.exception.UserNotFoundException;
import ru.priamosudov.hotelme.user.domain.SecuredUser;
import ru.priamosudov.hotelme.user.repository.SecuredUserRepository;
import ru.priamosudov.hotelme.user.service.SecuredUserService;

import java.util.Optional;

@Service
@Slf4j
public class SecuredUserServiceImpl implements SecuredUserService {

    private final SecuredUserRepository securedUserRepository;

    public SecuredUserServiceImpl(SecuredUserRepository securedUserRepository) {
        this.securedUserRepository = securedUserRepository;
    }

    @Override
    public SecuredUser getSecuredUserByUsername(String username) {
        log.debug("Try to find secured user with username = [{}]", username);
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Username must not be null");
        }

        return securedUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void addUser(SecuredUser securedUser) {
        Optional<SecuredUser> userOptional = securedUserRepository.findByUsername(securedUser.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException(securedUser.getUsername());
        }

        securedUserRepository.save(securedUser);
        log.debug("New user with username = [{}] was created", securedUser.getUsername());
    }
}
