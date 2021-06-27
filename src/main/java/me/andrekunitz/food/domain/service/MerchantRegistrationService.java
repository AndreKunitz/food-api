package me.andrekunitz.food.domain.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantRegistrationService {

	public static final String MERCHANT_NOT_FOUND_MSG = "There is no Merchant with id %d.";

	private final MerchantRepository merchantRepository;
	private final CuisineRegistrationService cuisineRegistrationService;

	public Merchant save(Merchant merchant) {
		Cuisine cuisine = cuisineRegistrationService.fetchOrFail(merchant.getCuisine().getId());
		merchant.setCuisine(cuisine);

		return merchantRepository.save(merchant);
	}

	public Merchant fetchOrFail(Long id) {
		return merchantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(MERCHANT_NOT_FOUND_MSG, id)));
	}
}
