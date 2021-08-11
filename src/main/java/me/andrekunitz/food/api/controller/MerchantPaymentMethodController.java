package me.andrekunitz.food.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.PaymentMethodModelAssembler;
import me.andrekunitz.food.api.model.PaymentMethodModel;
import me.andrekunitz.food.domain.service.MerchantRegistrationService;

@RestController
@RequestMapping("/merchants/{merchantId}/payment-methods")
@RequiredArgsConstructor
public class MerchantPaymentMethodController {

	private final MerchantRegistrationService merchantRegistration;
	private final PaymentMethodModelAssembler paymentMethodModelAssembler;

	@GetMapping
	public List<PaymentMethodModel> list(@PathVariable Long merchantId) {
		var merchant = merchantRegistration.fetchOrFail(merchantId);

		return paymentMethodModelAssembler.toCollectionModel(merchant.getPaymentMethods());
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long merchantId,
							 @PathVariable Long paymentMethodId
	) {
		merchantRegistration.disassociatePaymentMethod(merchantId, paymentMethodId);
	}

	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long merchantId,
						  @PathVariable Long paymentMethodId
	) {
		merchantRegistration.associatePaymentMethod(merchantId, paymentMethodId);
	}
}
