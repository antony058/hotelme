package ru.priamosudov.hotelme.user.service;

import ru.priamosudov.hotelme.user.domain.User;
import ru.priamosudov.hotelme.user.domain.SecuredUser;

public interface UserService {

    User getUserByUsername(String username);

    void addUser(User user);
}
