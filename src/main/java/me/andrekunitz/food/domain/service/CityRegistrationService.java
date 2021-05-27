package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.CityRepository;
import me.andrekunitz.food.domain.repository.StateRepository;

@Service
@RequiredArgsConstructor
public class CityRegistrationService {

	private final CityRepository cityRepository;
	private final StateRepository stateRepository;

	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateRepository.findById(stateId);

		if (state == null) {
			throw new EntityNotFoundException(
					String.format("Does not exist a state registered with an id %d.", stateId));
		}

		city.setState(state);

		return cityRepository.save(city);
	}

	public void remove(Long id) {
		try {
			cityRepository.remove(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format("Does not exist a city registered with an id %d.", id)
			);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("State with %d is in use and cannot be removed.", id)
			);
		}
	}
}
