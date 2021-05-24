package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.Cuisine;

import java.util.List;

public interface CuisinesRepository {
    List<Cuisine> findAll();

    Cuisine findById(Long id);

    Cuisine save(Cuisine cuisine);

    void remove(Long id);
}
