package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.Permission;
import me.andrekunitz.food.domain.model.State;

import java.util.List;

public interface StateRepository {
	List<State> findAll();

	State findById(Long id);

	State save(State state);

	void remove(State state);
}
