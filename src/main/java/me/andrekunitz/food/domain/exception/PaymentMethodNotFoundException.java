package me.andrekunitz.food.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

	public PaymentMethodNotFoundException(String message) {
		super(message);
	}

	public PaymentMethodNotFoundException(Long id) {
		this(String.format("Payment method with id %d does not exists.", id));
	}
}
