package me.andrekunitz.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.input.PaymentMethodInput;
import me.andrekunitz.food.domain.model.PaymentMethod;

@Component
@RequiredArgsConstructor
public class PaymentMethodInputDisassembler {

	private final ModelMapper modelMapper;

	public PaymentMethod toDomainObject(PaymentMethodInput paymentMethodInput) {
		return modelMapper.map(paymentMethodInput, PaymentMethod.class);
	}

	public void copyToDomainObject(PaymentMethodInput paymentMethodInput, PaymentMethod paymentMethod) {
		modelMapper.map(paymentMethodInput, paymentMethod);
	}
}
