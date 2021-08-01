package me.andrekunitz.food.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantModel {

	private Long id;
	private String name;
	private BigDecimal deliveryFee;
	private CuisineModel cuisine;
	private Boolean active;
}
