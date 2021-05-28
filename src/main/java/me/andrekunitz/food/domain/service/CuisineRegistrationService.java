package me.andrekunitz.food.domain.service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuisineRegistrationService {

	private final CuisinesRepository cuisinesRepository;

	public Cuisine save(Cuisine cuisine) {
		return cuisinesRepository.save(cuisine);
	}

	public void remove(Long id) {
		try {
			cuisinesRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format("Does not exist a cuisine registered with an id %d.", id)
			);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("Cuisine with %d is in use and cannot be removed.", id)
			);
		}
	}
}
