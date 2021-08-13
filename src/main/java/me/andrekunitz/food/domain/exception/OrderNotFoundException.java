package me.andrekunitz.food.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(Long id) {
		this(String.format("Order with id %d does not exists.", id));
	}
}
