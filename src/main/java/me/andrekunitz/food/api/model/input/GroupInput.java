package me.andrekunitz.food.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupInput {

	@NotBlank
	private String name;
}
