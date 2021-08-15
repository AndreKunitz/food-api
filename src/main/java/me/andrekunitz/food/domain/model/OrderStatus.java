package me.andrekunitz.food.domain.model;

public enum OrderStatus {

	REGISTERED("Registered"),
	CONFIRMED("Confirmed"),
	DELIVERED("Delivered"),
	CANCELED("Canceled");

	private String description;

	OrderStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
