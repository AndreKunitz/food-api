package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.CuisineNotFoundException;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;

@Service
@RequiredArgsConstructor
public class CuisineRegistrationService {

	public static final String CUISINE_IN_USE = "Cuisine with %d is in use and cannot be removed.";

	private final CuisinesRepository cuisinesRepository;

	@Transactional
	public Cuisine save(Cuisine cuisine) {
		return cuisinesRepository.save(cuisine);
	}

	@Transactional
	public void remove(Long id) {
		try {
			cuisinesRepository.deleteById(id);
			cuisinesRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CuisineNotFoundException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(CUISINE_IN_USE, id));
		}
	}

	public Cuisine fetchOrFail(Long id) {
		return cuisinesRepository.findById(id)
				.orElseThrow(() -> new CuisineNotFoundException(id));
	}
}
