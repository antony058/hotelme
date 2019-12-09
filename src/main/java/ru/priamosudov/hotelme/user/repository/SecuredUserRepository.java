package ru.priamosudov.hotelme.user.repository;

import org.springframework.data.repository.CrudRepository;
import ru.priamosudov.hotelme.user.domain.SecuredUser;

import java.util.Optional;

public interface SecuredUserRepository extends CrudRepository<SecuredUser, Long> {

    Optional<SecuredUser> findByUsername(String username);
}
