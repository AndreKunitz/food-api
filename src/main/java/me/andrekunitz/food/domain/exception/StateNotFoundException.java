package me.andrekunitz.food.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

	public StateNotFoundException(String message) {
		super(message);
	}

	public StateNotFoundException(Long stateId) {
		this(String.format("Does not exist a state registered with an id %d.", stateId));
	}
}
