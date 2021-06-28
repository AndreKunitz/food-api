package me.andrekunitz.food.domain.exception;

public class MerchantNotFoundException extends EntityNotFoundException {

	public MerchantNotFoundException(String message) {
		super(message);
	}

	public MerchantNotFoundException(Long merchantId) {
		this(String.format("Does not exist a merchant registered with an id %d.", merchantId));
	}
}
