package me.andrekunitz.food.domain.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import me.andrekunitz.food.domain.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantRegistrationService {

	private final MerchantRepository merchantRepository;
	private final CuisinesRepository cuisinesRepository;

	public Merchant save(Merchant merchant) {
		Long cuisineId = merchant.getCuisine().getId();
		Cuisine cuisine = cuisinesRepository.findById(cuisineId);

		if (cuisine == null) {
			throw new EntityNotFoundException(
					String.format("There is no Cuisine with id %d.", cuisineId));
		}

		merchant.setCuisine(cuisine);

		return merchantRepository.save(merchant);
	}
}
