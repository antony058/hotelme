package ru.priamosudov.hotelme.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.priamosudov.hotelme.user.UserNotFoundException;
import ru.priamosudov.hotelme.user.domain.SecuredUser;
import ru.priamosudov.hotelme.user.repository.SecuredUserRepository;
import ru.priamosudov.hotelme.user.service.SecuredUserService;

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
                .orElseThrow(() -> new UserNotFoundException("Secured user was not found", username));
    }
}
