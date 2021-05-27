package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class StateRepositoryImpl implements StateRepository {
	@PersistenceContext
	EntityManager manager;

	@Override
	public List<State> findAll() {
		return manager.createQuery("from State", State.class)
				.getResultList();
	}

	@Override
	public State findById(Long id) {
		return manager.find(State.class, id);
	}

	@Transactional
	@Override
	public State save(State state) {
		return manager.merge(state);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		var state = findById(id);
		if (state == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(state);
	}
}
