package ru.priamosudov.hotelme.user.service;

import ru.priamosudov.hotelme.user.domain.User;

public interface UserService {

    User getUserByUsername(String username);
}
