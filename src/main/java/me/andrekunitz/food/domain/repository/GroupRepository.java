package me.andrekunitz.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
