package me.andrekunitz.food.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderModel {

	private String code;
	private BigDecimal subtotal;
	private BigDecimal deliveryFee;
	private BigDecimal totalPrice;
	private String status;
	private OffsetDateTime registrationTimestamp;
	private OffsetDateTime confirmationTimestamp;
	private OffsetDateTime deliveredTimestamp;
	private OffsetDateTime cancellationTimestamp;
	private MerchantSummaryModel merchant;
	private UserModel client;
	private PaymentMethodModel paymentMethod;
	private AddressModel address;
	private List<OrderLineItemModel> items;

}
