package me.andrekunitz.food.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.MerchantNotFoundException;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantRegistrationService {

	private final MerchantRepository merchantRepository;
	private final CuisineRegistrationService cuisineRegistration;
	private final CityRegistrationService cityRegistration;
	private final PaymentMethodRegistrationService paymentMethodRegistration;
	private final UserRegistrationService userRegistration;

	@Transactional
	public Merchant save(Merchant merchant) {
		Cuisine cuisine = cuisineRegistration.fetchOrFail(merchant.getCuisine().getId());
		City city = cityRegistration.fetchOrFail(merchant.getAddress().getCity().getId());

		merchant.setCuisine(cuisine);
		merchant.getAddress().setCity(city);

		return merchantRepository.save(merchant);
	}

	@Transactional
	public void activate(Long id) {
		var merchant = this.fetchOrFail(id);

		merchant.activate();
	}

	@Transactional
	public void deactivate(Long id) {
		var merchant = this.fetchOrFail(id);

		merchant.deactivate();
	}

	@Transactional
	public void activate(List<Long> merchantIds) {
		merchantIds.forEach(this::activate);
	}

	@Transactional
	public void deactivate(List<Long> merchantIds) {
		merchantIds.forEach(this::deactivate);
	}


	@Transactional
	public void disassociatePaymentMethod(Long merchantId, Long paymentMethodId) {
		var merchant = fetchOrFail(merchantId);
		var paymentMethod = paymentMethodRegistration.fetchOrFail(paymentMethodId);

		merchant.removePaymentMethod(paymentMethod);
	}

	@Transactional
	public void associatePaymentMethod(Long merchantId, Long paymentMethodId) {
		var merchant = fetchOrFail(merchantId);
		var paymentMethod = paymentMethodRegistration.fetchOrFail(paymentMethodId);

		merchant.addPaymentMethod(paymentMethod);
	}

	@Transactional
	public void open(Long id) {
		var merchant = fetchOrFail(id);

		merchant.open();
	}

	@Transactional
	public void close(Long id) {
		var merchant = fetchOrFail(id);

		merchant.close();
	}
	public Merchant fetchOrFail(Long id) {
		return merchantRepository.findById(id)
				.orElseThrow(() -> new MerchantNotFoundException(id));
	}

	@Transactional
	public void associateResponsible(Long merchantId, Long userId) {
		var merchant = fetchOrFail(merchantId);
		var user = userRegistration.fetchOrFail(userId);

		merchant.addResponsible(user);
	}

	@Transactional
	public void disassociateResponsible(Long merchantId, Long userId) {
		var merchant = fetchOrFail(merchantId);
		var user = userRegistration.fetchOrFail(userId);

		merchant.removeResponsible(user);
	}
}
