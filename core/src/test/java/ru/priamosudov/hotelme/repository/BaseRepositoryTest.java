package ru.priamosudov.hotelme.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.priamosudov.core.common.domain.DomainModel;

import java.io.Serializable;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest(showSql = false)
public abstract class BaseRepositoryTest {

    @Autowired
    protected TestEntityManager entityManager;

    public <ID extends Serializable, T extends DomainModel<ID>> void addEntity(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }
}
