package me.andrekunitz.food.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.andrekunitz.food.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
