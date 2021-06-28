package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.CityNotFoundException;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.repository.CityRepository;

@Service
@RequiredArgsConstructor
public class CityRegistrationService {

	public static final String CITY_IN_USE_MSG = "City with %d is in use and cannot be removed.";

	private final CityRepository cityRepository;
	private final StateRegistrationService stateRegistrationService;

	public City save(City city) {
		var state = stateRegistrationService.fetchOrFail(city.getState().getId());
		city.setState(state);

		return cityRepository.save(city);
	}

	public void remove(Long id) {
		try {
			cityRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(CITY_IN_USE_MSG, id));
		}
	}

	public City fetchOrFail(Long id) {
		return cityRepository.findById(id)
				.orElseThrow(() -> new CityNotFoundException(id));
	}
}
