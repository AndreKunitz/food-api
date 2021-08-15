package me.andrekunitz.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findByCode(String code);

	@Query("from Order o join fetch o.client join o.merchant m join fetch m.cuisine")
	List<Order> findAll();
}
