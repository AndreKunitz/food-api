package me.andrekunitz.food.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {

	public CuisineNotFoundException(String message) {
		super(message);
	}

	public CuisineNotFoundException(Long cuisineId) {
		this(String.format("Does not exist a cuisine registered with an id %d.", cuisineId));
	}
}
