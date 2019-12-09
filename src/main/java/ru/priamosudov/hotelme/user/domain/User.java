package ru.priamosudov.hotelme.user.domain;

import ru.priamosudov.hotelme.common.domain.DomainModel;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@SequenceGenerator(name = DomainModel.SEQ_GENERATOR_NAME, sequenceName = "seq_user_id", allocationSize = 1)
public class User extends BaseUser {

}
