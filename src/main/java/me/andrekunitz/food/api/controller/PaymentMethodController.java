package me.andrekunitz.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.PaymentMethodInputDisassembler;
import me.andrekunitz.food.api.assembler.PaymentMethodModelAssembler;
import me.andrekunitz.food.api.model.PaymentMethodModel;
import me.andrekunitz.food.api.model.input.PaymentMethodInput;
import me.andrekunitz.food.domain.repository.PaymentMethodRepository;
import me.andrekunitz.food.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

	private final PaymentMethodRepository paymentMethodRepository;
	private final PaymentMethodRegistrationService paymentMethodRegistration;
	private final PaymentMethodModelAssembler paymentMethodModelAssembler;
	private final PaymentMethodInputDisassembler paymentMethodInputDisassembler;

	@GetMapping
	public List<PaymentMethodModel> list() {
		var paymentMethods = paymentMethodRepository.findAll();

		return paymentMethodModelAssembler.toCollectionModel(paymentMethods);
	}

	@GetMapping("/{id}")
	public PaymentMethodModel search(@PathVariable Long id) {
		return paymentMethodModelAssembler.toModel(
				paymentMethodRegistration.fetchOrFail(id));
	}

	@PostMapping
	public PaymentMethodModel add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		var paymentMethod = paymentMethodInputDisassembler.toDomainObject(paymentMethodInput);

		return paymentMethodModelAssembler.toModel(
				paymentMethodRegistration.save(paymentMethod));
	}

	@PutMapping("/{id}")
	public PaymentMethodModel update(@PathVariable Long id,
									 @RequestBody @Valid PaymentMethodInput paymentMethodInput
	) {
		var currentPaymentMethod = paymentMethodRegistration.fetchOrFail(id);
		paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, currentPaymentMethod);

		return paymentMethodModelAssembler.toModel(
				paymentMethodRegistration.save(currentPaymentMethod));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		paymentMethodRegistration.remove(id);
	}
}
