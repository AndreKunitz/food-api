package me.andrekunitz.food.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MerchantIdInput {

	@NotNull
	private Long id;

}
