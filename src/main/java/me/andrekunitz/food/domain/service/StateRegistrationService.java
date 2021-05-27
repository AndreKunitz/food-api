package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;

@Service
@RequiredArgsConstructor
public class StateRegistrationService {

	private final StateRepository stateRepository;

	public State save(State state) {
		return stateRepository.save(state);
	}

	public void remove(Long id) {
		try {
			stateRepository.remove(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format("Does not exist a state registered with an id %d.", id)
			);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("State with %d is in use and cannot be removed.", id)
			);
		}
	}
}
