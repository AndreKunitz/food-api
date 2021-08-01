package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.PaymentMethodModel;
import me.andrekunitz.food.domain.model.PaymentMethod;

@Component
@RequiredArgsConstructor
public class PaymentMethodModelAssembler {

	private final ModelMapper modelMapper;

	public PaymentMethodModel toModel(PaymentMethod paymentMethod) {
		return modelMapper.map(paymentMethod, PaymentMethodModel.class);
	}

	public List<PaymentMethodModel> toCollectionModel(List<PaymentMethod> paymentMethods) {
		return paymentMethods.stream()
				.map(paymentMethod -> toModel(paymentMethod))
				.collect(Collectors.toList());
	}
}
