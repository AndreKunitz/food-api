package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.repository.CityRepository;
import me.andrekunitz.food.domain.service.CityRegistrationService;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

	private final CityRepository cityRepository;
	private final CityRegistrationService cityRegistration;

	@GetMapping
	public List<City> list() {
		return cityRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<City> search(@PathVariable Long id) {
		var city = cityRepository.findById(id);

		if (city.isPresent()) {
			return ResponseEntity.ok(city.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody City city) {
		try {
			city = cityRegistration.save(city);

			return ResponseEntity.status(CREATED)
					.body(city);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
	                                @RequestBody City city) {

		try {
			City currentCity = cityRepository.findById(id).orElse(null);

			if (currentCity != null) {
				BeanUtils.copyProperties(city, currentCity, "id");

				currentCity = cityRegistration.save(currentCity);
				return ResponseEntity.ok(currentCity);
			}

			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {
		try {
			cityRegistration.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(CONFLICT)
					.body(e.getMessage());
		}
	}
}
