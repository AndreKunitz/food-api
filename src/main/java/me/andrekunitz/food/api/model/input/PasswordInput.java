package me.andrekunitz.food.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordInput {

	@NotBlank
	private String currentPassword;

	@NotBlank
	private String newPassword;
}
