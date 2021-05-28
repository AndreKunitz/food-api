package me.andrekunitz.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
