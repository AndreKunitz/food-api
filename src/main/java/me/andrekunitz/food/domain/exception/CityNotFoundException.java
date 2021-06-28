package me.andrekunitz.food.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

	public CityNotFoundException(String message) {
		super(message);
	}

	public CityNotFoundException(Long cityId) {
		this(String.format("Does not exist a city registered with an id %d.", cityId));
	}
}
