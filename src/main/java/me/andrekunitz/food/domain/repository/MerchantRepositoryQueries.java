package me.andrekunitz.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import me.andrekunitz.food.domain.model.Merchant;

public interface MerchantRepositoryQueries {

	List<Merchant> find(String name,
	                    BigDecimal initialFee,
	                    BigDecimal finalFee);
}
