package me.andrekunitz.food.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("orderFilter")
@Setter
@Getter
public class OrderSummaryModel {

	private String code;
	private BigDecimal subtotal;
	private BigDecimal deliveryFee;
	private BigDecimal totalPrice;
	private String status;
	private OffsetDateTime registrationTimestamp;
	private MerchantSummaryModel merchant;
	private UserModel client;

}
