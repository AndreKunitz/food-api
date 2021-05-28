package me.andrekunitz.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
