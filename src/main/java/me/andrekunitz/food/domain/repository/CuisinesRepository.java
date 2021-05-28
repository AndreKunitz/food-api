package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.Cuisine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisinesRepository extends JpaRepository<Cuisine, Long> {

//    List<Cuisine> findByName(String name);
}
