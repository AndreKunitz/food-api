package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;
import me.andrekunitz.food.domain.service.MerchantRegistrationService;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

	private final MerchantRepository merchantRepository;
	private final MerchantRegistrationService merchantRegistration;

	@GetMapping
	public List<Merchant> list() {
		return merchantRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Merchant> search(@PathVariable Long id) {
		 var merchant = merchantRepository.findById(id);
		 if (merchant.isPresent()) {
		 	return ResponseEntity.ok(merchant.get());
		 }
		 return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Merchant merchant) {
		try {
			merchant = merchantRegistration.save(merchant);

			return ResponseEntity.status(CREATED).body(merchant);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
	                                @RequestBody Merchant merchant) {
		try {
			var currentMerchant = merchantRepository.findById(id).orElse(null);

			if (currentMerchant != null) {
				BeanUtils.copyProperties(merchant, currentMerchant, "id");

				currentMerchant = merchantRegistration.save(currentMerchant);
				return ResponseEntity.ok(currentMerchant);
			}

			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id,
	                                       @RequestBody Map<String, Object> fields) {

		var currentMerchant = merchantRepository.findById(id).orElse(null);

		if (currentMerchant == null) {
			return ResponseEntity.notFound().build();
		}
		merge(fields, currentMerchant);

		return update(id, currentMerchant);
	}

	private void merge(Map<String, Object> originFields, Merchant destinationMerchant) {
		var objectMapper = new ObjectMapper();
		var originMerchant = objectMapper.convertValue(originFields, Merchant.class);

		originFields.forEach((key, value) -> {
			var field = ReflectionUtils.findField(Merchant.class, key);
			field.setAccessible(true);

			var newValue = ReflectionUtils.getField(field, originMerchant);

			ReflectionUtils.setField(field, destinationMerchant, newValue);
		});
	}

	@GetMapping("/by-delivery-fee")
	public List<Merchant> merchantsByDeliveryFee(
			BigDecimal initialFee, BigDecimal finalFee) {
		return merchantRepository.queryByDeliveryFeeBetween(initialFee, finalFee);
	}

	@GetMapping("/by-name-and-cuisine")
	public List<Merchant> merchantsByNameAndCuisine(
			String name, Long cuisineId) {
		return merchantRepository.findByNameContainingAndCuisineId(name, cuisineId);
	}

	@GetMapping("/first-by-name")
	public Optional<Merchant> merchantsFirstByName(String name) {
		return merchantRepository.findFirstMerchantByNameContaining(name);
	}

	@GetMapping("/top2-por-nome")
	public List<Merchant> merchantsTop2ByName(String name) {
		return merchantRepository.findTop2ByNameContaining(name);
	}

	@GetMapping("/count-by-cuisine")
	public int merchantsCountByCuisine(Long cuisineId) {
		return merchantRepository.countByCuisineId(cuisineId);
	}
}
