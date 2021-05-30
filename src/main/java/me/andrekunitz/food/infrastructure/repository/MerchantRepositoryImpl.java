package me.andrekunitz.food.infrastructure.repository;

import static me.andrekunitz.food.infrastructure.repository.specification.MerchantSpecification.withFreeDelivery;
import static me.andrekunitz.food.infrastructure.repository.specification.MerchantSpecification.withSimilarName;
import static org.springframework.util.StringUtils.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;
import me.andrekunitz.food.domain.repository.MerchantRepositoryQueries;

@Repository
public class MerchantRepositoryImpl implements MerchantRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired @Lazy
	private MerchantRepository merchantRepository;

	@Override
	public List<Merchant> find(String name,
	                           BigDecimal initialFee,
	                           BigDecimal finalFee) {

		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Merchant.class);
		var root = criteria.from(Merchant.class);

		var predicates = new ArrayList<Predicate>();

		if (hasText(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		if (initialFee != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryFee"), initialFee));
		}
		if (finalFee != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("deliveryFee"), finalFee));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

	public List<Merchant> findWithFreeDelivery(String name) {
		return merchantRepository.findAll(withFreeDelivery().and(withSimilarName(name)));
	}
}
