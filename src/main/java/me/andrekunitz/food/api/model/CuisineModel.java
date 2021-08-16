package me.andrekunitz.food.api.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import me.andrekunitz.food.api.model.view.MerchantView;

@Setter
@Getter
public class CuisineModel {

	@JsonView(MerchantView.Summary.class)
	private Long id;

	@JsonView(MerchantView.Summary.class)
	private String name;

}
