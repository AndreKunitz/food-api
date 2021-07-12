package me.andrekunitz.food.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateInput {

	@NotBlank
	private String name;

}
