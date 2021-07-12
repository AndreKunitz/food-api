package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.MerchantInputDisassembler;
import me.andrekunitz.food.api.assembler.MerchantModelAssembler;
import me.andrekunitz.food.api.model.MerchantModel;
import me.andrekunitz.food.api.model.input.MerchantInput;
import me.andrekunitz.food.core.validation.ValidationException;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.CuisineNotFoundException;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;
import me.andrekunitz.food.domain.service.MerchantRegistrationService;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

	private final MerchantRepository merchantRepository;
	private final MerchantRegistrationService merchantRegistrationService;
	private final SmartValidator validator;
	private final MerchantModelAssembler merchantModelAssembler;
	private final MerchantInputDisassembler merchantInputDisassembler;

	@GetMapping
	public List<MerchantModel> list() {
		return merchantModelAssembler.toCollectionModel(
				merchantRepository.findAll());
	}

	@GetMapping("/{id}")
	public MerchantModel search(@PathVariable Long id) {
		return merchantModelAssembler.toModel(
				merchantRegistrationService.fetchOrFail(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public MerchantModel add(@RequestBody @Valid MerchantInput merchantInput) {
		try {
			var merchant = merchantInputDisassembler.toDomainObject(merchantInput);

			return merchantModelAssembler.toModel(
					merchantRegistrationService.save(merchant));
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public MerchantModel update(@PathVariable Long id,
	                            @RequestBody @Valid MerchantInput merchantInput
	) {
		var currentMerchant = merchantRegistrationService.fetchOrFail(id);

		merchantInputDisassembler.copyToDomainObject(merchantInput, currentMerchant);

		try {
			return merchantModelAssembler.toModel(
					merchantRegistrationService.save(currentMerchant));
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@PatchMapping("/{id}")
	public MerchantModel partialUpdate(@PathVariable Long id,
	                              @RequestBody Map<String, Object> fields,
	                              HttpServletRequest request
	) {
		var currentMerchant = merchantRegistrationService.fetchOrFail(id);
		merge(fields, currentMerchant, request);
		validate(currentMerchant, "merchant");

		return update(id, merchantInputDisassembler.toInputAssembler(currentMerchant));
	}

	private void validate(Merchant merchant, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(merchant, objectName);
		validator.validate(merchant, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
	}

	private void merge(Map<String, Object> originFields, Merchant destinationMerchant, HttpServletRequest request) {
		var servletServerHttpRequest = new ServletServerHttpRequest(request);

		try {
			var objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			var originMerchant = objectMapper.convertValue(originFields, Merchant.class);

			originFields.forEach((key, value) -> {
				var field = ReflectionUtils.findField(Merchant.class, key);
				field.setAccessible(true);

				var newValue = ReflectionUtils.getField(field, originMerchant);

				ReflectionUtils.setField(field, destinationMerchant, newValue);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
		}
	}

	@GetMapping("/by-delivery-fee")
	public List<MerchantModel> merchantsByDeliveryFee(
			BigDecimal initialFee, BigDecimal finalFee) {
		return merchantModelAssembler.toCollectionModel(
				merchantRepository.queryByDeliveryFeeBetween(initialFee, finalFee));
	}

	@GetMapping("/by-name-and-cuisine")
	public List<MerchantModel> merchantsByNameAndCuisine(
			String name, Long cuisineId) {
		return merchantModelAssembler.toCollectionModel(
				merchantRepository.findByNameAndCuisine(name, cuisineId));
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

	@GetMapping("by-name-and-fee")
	public List<Merchant> merchantsByNameAndFee(String name,
	                                            BigDecimal initialFee,
	                                            BigDecimal finalFee) {

		return merchantRepository.find(name, initialFee, finalFee);
	}

	@GetMapping("/free-delivery")
	public List<Merchant> merchantsWithFreeDelivery(String name) {
		return merchantRepository.findWithFreeDelivery(name);
	}
}
