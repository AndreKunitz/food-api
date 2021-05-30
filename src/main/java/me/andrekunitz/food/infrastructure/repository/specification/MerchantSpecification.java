package me.andrekunitz.food.infrastructure.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import me.andrekunitz.food.domain.model.Merchant;

public class MerchantSpecification {

	public static Specification<Merchant> withFreeDelivery() {
		return (root, query, builder) -> builder.equal(root.get("deliveryFee"), BigDecimal.ZERO);
	}
	public static Specification<Merchant> withSimilarName(String name) {
		return (root, query, builder) ->builder.like(root.get("name"), "%" + name + "%");
	}

}
