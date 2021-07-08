package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.StateNotFoundException;
import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;

@Service
@RequiredArgsConstructor
public class StateRegistrationService {

	public static final String STATE_IN_USE_MSG = "State with %d is in use and cannot be removed.";

	private final StateRepository stateRepository;

	@Transactional
	public State save(State state) {
		return stateRepository.save(state);
	}

	@Transactional
	public void remove(Long id) {
		try {
			stateRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(STATE_IN_USE_MSG, id));
		}
	}

	public State fetchOrFail(Long id) {
		return stateRepository.findById(id)
				.orElseThrow(() -> new StateNotFoundException(id));
	}
}
