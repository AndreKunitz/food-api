package me.andrekunitz.food.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderLineItemInput {

	@NotNull
	private Long productId;

	@NotNull
	@PositiveOrZero
	private Integer quantity;

	private String observation;

}
