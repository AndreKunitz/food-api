package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import me.andrekunitz.food.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping("/cuisines")
@RequiredArgsConstructor
public class CuisineController {

	private final CuisinesRepository cuisinesRepository;
	private final CuisineRegistrationService cuisineRegistration;

	@GetMapping
	public List<Cuisine> list() {
		return cuisinesRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cuisine> search(@PathVariable Long id) {
		var cuisine = cuisinesRepository.findById(id);
		if(cuisine.isPresent()) {
			return ResponseEntity.ok(cuisine.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public Cuisine add(@RequestBody Cuisine cuisine) {
		return cuisineRegistration.save(cuisine);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cuisine> update(@PathVariable Long id,
	                                      @RequestBody Cuisine cuisine) {
		var currentCuisine = cuisinesRepository.findById(id);
		if (currentCuisine.isPresent()) {
			BeanUtils.copyProperties(cuisine, currentCuisine.get(), "id");
			var savedCuisine = cuisineRegistration.save(currentCuisine.get());
			return ResponseEntity.ok(savedCuisine);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cuisine> remove(@PathVariable Long id) {
		try {
			cuisineRegistration.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (EntityInUseException e) {
			return ResponseEntity.status(CONFLICT).build();
		}
	}

	@GetMapping("/by-name")
	public List<Cuisine> cuisinesByName(String name) {
		return cuisinesRepository.findAllByNameContaining(name);
	}

	@GetMapping("/unique-by-name")
	public Optional<Cuisine> cuisineByName(String name) {
		return cuisinesRepository.findByName(name);
	}

	@GetMapping("/exists")
	public boolean cuisineExists(String name) {
		return cuisinesRepository.existsByName(name);
	}
}
