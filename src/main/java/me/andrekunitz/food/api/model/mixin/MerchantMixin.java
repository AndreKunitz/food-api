package me.andrekunitz.food.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.andrekunitz.food.domain.model.Address;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.PaymentMethod;
import me.andrekunitz.food.domain.model.Product;

public abstract class MerchantMixin {

	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private Cuisine cuisine;

	@JsonIgnore
	private Address address;

	@JsonIgnore
	private OffsetDateTime registrationDate;

	@JsonIgnore
	private OffsetDateTime updateDate;

	@JsonIgnore
	private List<PaymentMethod> paymentMethods = new ArrayList<>();

	@JsonIgnore
	private List<Product> products = new ArrayList<>();
}
