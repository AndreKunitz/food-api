package me.andrekunitz.food.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

	public OrderNotFoundException(String code) {
		super(String.format("Order with id %d does not exists.", code));
	}

}
