package me.andrekunitz.food.infrastructure.repository.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.filter.DailySaleFilter;
import me.andrekunitz.food.domain.model.Order;
import me.andrekunitz.food.domain.model.OrderStatus;
import me.andrekunitz.food.domain.model.dto.DailySale;
import me.andrekunitz.food.domain.service.SaleQueryService;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

	@PersistenceContext
	private EntityManager manager;

	public static final String REGISTRATION_TIMESTAMP = "registrationTimestamp";

	/**
	* <h2>Criteria implementation that maps the following SQL query:</h2>
	*
	*{@code
	* SELECT
	*   DATE(CURRENT_TZ(o.registration_timestamp, '+00:00', '-03:00')) as date,
	* 	COUNT(o.id) as total_sale,.
	* 	SUM(o.total_price) as total_billed
	*
	* FROM Order o
	*
	* WHERE o.status in ('CONFIRMED', 'DELIVERED')
	*	AND o.merchant_id = ?
	*	AND o.registration_timestamp>=?
	*	AND o.registration_timestamp<=?
	*
	* GROUP BY DATE(o.registration_timestamp)
	* }
	*/
	@Override
	public List<DailySale> searchDailySale(DailySaleFilter filter, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(DailySale.class);
		var root = query.from(Order.class);
		var predicates = new ArrayList<Predicate>();

		var functionConvertTzDateTime = builder.function(
				"convert_tz", Date.class, root.get(REGISTRATION_TIMESTAMP),
				builder.literal("+00:00"), builder.literal(timeOffset));

		var functionDateDateTime = builder.function(
				"date", Date.class, functionConvertTzDateTime);

		var selection = builder.construct(DailySale.class,
				functionDateDateTime,
				builder.count(root.get("id")),
				builder.sum(root.get("totalPrice")));

		if (filter.getMerchantId() != null) {
			predicates.add(builder.equal(root.get("merchant"), filter.getMerchantId()));
		}
		if (filter.getDateTimeBeginning() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(REGISTRATION_TIMESTAMP),
					filter.getDateTimeBeginning()));
		}
		if (filter.getDateTimeEnding() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(REGISTRATION_TIMESTAMP),
					filter.getDateTimeEnding()));
		}

		predicates.add(root.get("status").in(
				OrderStatus.REGISTERED, OrderStatus.DELIVERED));

		query.select(selection);
		query.groupBy(functionDateDateTime);
		query.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(query).getResultList();
	}
}
