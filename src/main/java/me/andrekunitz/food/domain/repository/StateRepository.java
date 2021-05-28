package me.andrekunitz.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
