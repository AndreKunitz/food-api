package me.andrekunitz.food.infrastructure.repository.specification;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import me.andrekunitz.food.domain.model.Order;
import me.andrekunitz.food.domain.filter.OrderFilter;

public class OrderSpecification {

	public static Specification<Order> withFilter(OrderFilter filters) {
		return (root, query, builder) -> {
			// Skip fetch if result type differ, to avoid fetch exception in count query in pagination
			if (Order.class.equals(query.getResultType())) {
				root.fetch("merchant").fetch("cuisine");
				root.fetch("client");
			}

			var predicates = new ArrayList<Predicate>();

			if (filters.getClientId() != null) {
				predicates.add(builder.equal(root.get("client"), filters.getClientId()));
			}
			if (filters.getMerchantId() != null) {
				predicates.add(builder.equal(root.get("merchant"), filters.getMerchantId()));
			}
			if (filters.getDateTimeBeginning() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("registrationTimestamp"),
						filters.getDateTimeBeginning()));
			}
			if (filters.getDateTimeEnding() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("registrationTimestamp"),
						filters.getDateTimeEnding()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
