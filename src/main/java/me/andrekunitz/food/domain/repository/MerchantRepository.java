package me.andrekunitz.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

	List<Merchant> queryByDeliveryFeeBetween(BigDecimal initialFee, BigDecimal finalFee);

	List<Merchant> findByNameContainingAndCuisineId(String name, Long cuisineId);

	Optional<Merchant> findFirstMerchantByNameContaining(String name);

	List<Merchant> findTop2ByNameContaining(String name);

	int countByCuisineId(Long cozinha);
}
