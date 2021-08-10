package me.andrekunitz.food.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Long merchantId, Long productId) {
		this(String.format("Does not exists a product with an id %d registred in merchant with id %d",
				productId, merchantId));
	}
}
