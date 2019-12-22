package ru.priamosudov.hotelme.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.priamosudov.hotelme.user.domain.User;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class UserRepositoryTest extends BaseUserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldSuccessfullyFindUserByUserName() {
		Optional<User> actualUserOptional = userRepository.findByUsername(COMMON_USER_USERNAME);

		assertTrue("User was not found by user repository", actualUserOptional.isPresent());

		User actualUser = actualUserOptional.get();
		checkCommonUserData(actualUser, COMMON_USER_USERNAME);
	}

	@Test
	public void shouldNotFoundUserByUsername() {
		Optional<User> actualUserOptional = userRepository.findByUsername("_" + COMMON_USER_USERNAME);

		assertFalse("User was found by user repository", actualUserOptional.isPresent());
	}
}
