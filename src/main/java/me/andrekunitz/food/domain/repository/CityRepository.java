package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.City;

import java.util.List;

public interface CityRepository {
	List<City> findAll();

	City findById(Long id);

	City save(City city);

	void remove(City city);
}
