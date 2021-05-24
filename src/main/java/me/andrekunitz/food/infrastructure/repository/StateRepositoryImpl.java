package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;
import org.springframework.stereotype.Component;

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

	@Override
	public State save(State state) {
		return manager.merge(state);
	}

	@Override
	public void remove(State state) {
		state = findById(state.getId());
		manager.remove(state);
	}
}
