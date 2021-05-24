package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.PaymentMethod;
import me.andrekunitz.food.domain.repository.PaymentMethodRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<PaymentMethod> findAll() {
		return manager.createQuery("from PaymentMethod", PaymentMethod.class)
				.getResultList();
	}

	@Override
	public PaymentMethod findById(Long id) {
		return manager.find(PaymentMethod.class, id);
	}

	@Override
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return manager.merge(paymentMethod);
	}

	@Override
	public void remove(PaymentMethod paymentMethod) {
		paymentMethod = findById(paymentMethod.getId());
		manager.remove(paymentMethod);
	}
}
