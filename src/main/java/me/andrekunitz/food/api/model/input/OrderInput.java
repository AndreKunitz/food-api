package me.andrekunitz.food.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInput {

	@Valid
	@NotNull
	private MerchantIdInput merchant;

	@Valid
	@NotNull
	private AddressInput deliveryAddress;

	@Valid
	@NotNull
	private PaymentMethodIdInput paymentMethod;

	@Valid
	@Size(min = 1)
	@NotNull
	private List<OrderLineItemInput> items;

}
