package ru.priamosudov.hotelme.user.domain;

import lombok.Getter;
import lombok.Setter;
import ru.priamosudov.core.common.domain.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@SequenceGenerator(name = DomainModel.SEQ_GENERATOR_NAME, sequenceName = "seq_user_id", allocationSize = 1)
@Getter
@Setter
public class SecuredUser extends BaseUser {

    @Column(nullable = false)
    private String password;
}
