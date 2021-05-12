package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.Cuisine;

import java.util.List;

public interface CuisinesRepository {
    public List<Cuisine> findAll();

    public Cuisine findById(Long id);

    public Cuisine save(Cuisine cuisine);

    public void remove(Cuisine cuisine);
}
