package me.andrekunitz.food.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSummaryModel {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal deliveryFee;
	private BigDecimal totalPrice;
	private String status;
	private OffsetDateTime registrationTimestamp;
	private MerchantSummaryModel merchant;
	private UserModel client;

}
