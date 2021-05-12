package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodRepository {
	List<PaymentMethod> findAll();

	PaymentMethod findById(Long id);

	PaymentMethod save(PaymentMethod paymentMethod);

	void remove(PaymentMethod paymentMethod);
}
