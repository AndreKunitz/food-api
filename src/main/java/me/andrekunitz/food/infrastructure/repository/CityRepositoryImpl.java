package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.repository.CityRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CityRepositoryImpl implements CityRepository {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<City> findAll() {
		return manager.createQuery("from City", City.class)
				.getResultList();
	}

	@Override
	public City findById(Long id) {
		return manager.find(City.class, id);
	}

	@Override
	public City save(City city) {
		return manager.merge(city);
	}

	@Override
	public void remove(City city) {
		city = findById(city.getId());
		manager.remove(city);
	}
}
