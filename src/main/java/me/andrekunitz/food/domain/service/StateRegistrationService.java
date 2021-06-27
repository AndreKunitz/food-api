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

	public static final String STATE_NOT_FOUND_MSG = "Does not exist a state registered with an id %d.";
	public static final String STATE_IN_USE_MSG = "State with %d is in use and cannot be removed.";

	private final StateRepository stateRepository;

	public State save(State state) {
		return stateRepository.save(state);
	}

	public void remove(Long id) {
		try {
			stateRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format(STATE_NOT_FOUND_MSG, id));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(STATE_IN_USE_MSG, id));
		}
	}

	public State fetchOrFail(Long id) {
		return stateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
					String.format(STATE_NOT_FOUND_MSG, id)));
	}
}
