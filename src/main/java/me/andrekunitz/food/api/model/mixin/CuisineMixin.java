package me.andrekunitz.food.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import me.andrekunitz.food.domain.model.Merchant;

public abstract class CuisineMixin {

	@JsonIgnore
	private List<Merchant> merchants = new ArrayList<>();
}
