package me.andrekunitz.food.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

	REGISTERED("Registered"),
	CONFIRMED("Confirmed", REGISTERED),
	DELIVERED("Delivered", CONFIRMED),
	CANCELED("Canceled", REGISTERED, CONFIRMED);

	private String description;
	private List<OrderStatus> previousStatus;

	OrderStatus(String description, OrderStatus... previousStatus) {
		this.description = description;
		this.previousStatus = Arrays.asList(previousStatus);
	}

	public String getDescription() {
		return description;
	}

	public boolean cannotChangeTo(OrderStatus newStatus) {
		return !newStatus.previousStatus.contains(this);
	}
}
