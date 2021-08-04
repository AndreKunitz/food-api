package me.andrekunitz.food.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Long id) {
		this(String.format("Does not exist an user registered with an id %d.", id));
	}
}
