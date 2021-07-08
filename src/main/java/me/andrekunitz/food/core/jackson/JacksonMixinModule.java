package me.andrekunitz.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import me.andrekunitz.food.api.model.mixin.CityMixin;
import me.andrekunitz.food.api.model.mixin.CuisineMixin;
import me.andrekunitz.food.api.model.mixin.MerchantMixin;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;

@Component
public class JacksonMixinModule extends SimpleModule {

	public JacksonMixinModule() {
		setMixInAnnotation(Merchant.class, MerchantMixin.class);
		setMixInAnnotation(City.class, CityMixin.class);
		setMixInAnnotation(Cuisine.class, CuisineMixin.class);
	}
}
