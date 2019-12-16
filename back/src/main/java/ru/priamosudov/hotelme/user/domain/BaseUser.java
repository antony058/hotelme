package ru.priamosudov.hotelme.user.domain;

import lombok.Getter;
import lombok.Setter;
import ru.priamosudov.core.common.domain.DomainModel;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseUser extends DomainModel<Long> {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date birthDate;
}
