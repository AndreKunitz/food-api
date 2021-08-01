package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.PaymentMethodNotFoundException;
import me.andrekunitz.food.domain.model.PaymentMethod;
import me.andrekunitz.food.domain.repository.PaymentMethodRepository;

@Service
@RequiredArgsConstructor
public class PaymentMethodRegistrationService {

	public static final String MSG_PAYMENT_METHOD_IN_USE = "Payment method with id %d is in use and cannot be removed";
	private final PaymentMethodRepository paymentMethodRepository;

	@Transactional
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);
	}

	@Transactional
	public void remove(Long id) {
		try {
			paymentMethodRepository.deleteById(id);
			paymentMethodRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PaymentMethodNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_PAYMENT_METHOD_IN_USE, id));
		}
	}

	public PaymentMethod fetchOrFail(Long id) {
		return paymentMethodRepository.findById(id).orElseThrow(
				() -> new PaymentMethodNotFoundException(id)
		);
	}
}
