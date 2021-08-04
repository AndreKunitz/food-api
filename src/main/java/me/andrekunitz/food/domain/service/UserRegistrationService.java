package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.UserNotFoundException;
import me.andrekunitz.food.domain.model.User;
import me.andrekunitz.food.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

	private final UserRepository userRepository;

	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional
	public void changePassword(Long userId, String currentPassword, String newPassword) {
		var user = fetchOrFail(userId);

		if (user.passwordDoesNotMatch(currentPassword)) {
			throw new BusinessException("Current password entered does not match user password.");
		}

		user.setPassword(newPassword);
	}

	public User fetchOrFail(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
	}


}
