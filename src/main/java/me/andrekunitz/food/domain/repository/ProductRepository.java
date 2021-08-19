package me.andrekunitz.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("from Product where merchant.id = :merchant and id = :product")
	Optional<Product> findById(@Param("merchant") Long merchantId,
							   @Param("product") Long productId);

	List<Product> findAllByMerchant(Merchant merchant);

	@Query("from Product p where p.active = true and p.merchant = :merchant")
	List<Product> findActiveByMerchant(@Param("merchant") Merchant merchant);
}
