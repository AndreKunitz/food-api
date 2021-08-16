package me.andrekunitz.food.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import me.andrekunitz.food.api.model.view.MerchantView;

@Getter
@Setter
public class MerchantModel {

	@JsonView({ MerchantView.Summary.class, MerchantView.NameOnly.class })
	private Long id;

	@JsonView({ MerchantView.Summary.class, MerchantView.NameOnly.class })
	private String name;

	@JsonView(MerchantView.Summary.class)
	private BigDecimal deliveryFee;

	@JsonView(MerchantView.Summary.class)
	private CuisineModel cuisine;

	private Boolean active;
	private Boolean open;
	private AddressModel address;

}
