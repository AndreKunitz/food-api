package me.andrekunitz.food.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

	public GroupNotFoundException(String message) {
		super(message);
	}

	public GroupNotFoundException(Long id) {
		this(String.format("Group with id %d does not exists.", id));
	}
}
