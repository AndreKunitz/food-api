package me.andrekunitz.food.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.andrekunitz.food.domain.model.State;

public abstract class CityMixin {

	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private State state;
}
