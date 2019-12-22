package ru.priamosudov.hotelme.user.service;

import ru.priamosudov.hotelme.user.domain.SecuredUser;

public interface SecuredUserService {

    SecuredUser getSecuredUserByUsername(String username);

    void addUser(SecuredUser securedUser);
}
