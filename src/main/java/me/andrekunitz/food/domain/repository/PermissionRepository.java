package me.andrekunitz.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}

