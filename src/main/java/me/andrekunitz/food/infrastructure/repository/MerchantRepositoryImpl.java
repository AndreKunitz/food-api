package me.andrekunitz.food.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;
import me.andrekunitz.food.domain.repository.MerchantRepositoryQueries;

@Repository
public class MerchantRepositoryImpl implements MerchantRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Merchant> find(String name,
	                           BigDecimal initialFee,
	                           BigDecimal finalFee) {

		var jpql = new StringBuilder();
		jpql.append("from Merchant where 0=0 ");

		var parameters = new HashMap<String, Object>();

		if (StringUtils.hasLength(name)) {
			jpql.append("and name like :name ");
			parameters.put("name", "%" + name + "%");
		}

		if (initialFee != null) {
			jpql.append("and deliveryFee >= :initialFee ");
			parameters.put("initialFee", initialFee);
		}

		if (finalFee != null) {
			jpql.append("and deliveryFee <= :finalFee ");
			parameters.put("finalFee", finalFee);
		}

		var query = manager.createQuery(jpql.toString(), Merchant.class);
		parameters.forEach((key, value) -> query.setParameter(key, value));

		return query.getResultList();
	}
}
