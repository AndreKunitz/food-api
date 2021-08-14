package me.andrekunitz.food.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModel {

	private String zipCode;
	private String street;
	private String number;
	private String neighborhood;
	private CitySummaryModel city;
}
