package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.MerchantNotFoundException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantRegistrationService {

	private final MerchantRepository merchantRepository;
	private final CuisineRegistrationService cuisineRegistrationService;

	@Transactional
	public Merchant save(Merchant merchant) {
		Cuisine cuisine = cuisineRegistrationService.fetchOrFail(merchant.getCuisine().getId());
		merchant.setCuisine(cuisine);

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

	public Merchant fetchOrFail(Long id) {
		return merchantRepository.findById(id)
				.orElseThrow(() -> new MerchantNotFoundException(id));
	}
}
