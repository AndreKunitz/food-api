package me.andrekunitz.food.api.controller;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.CuisineXmlWrapper;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import me.andrekunitz.food.domain.service.CuisineRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

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

	@GetMapping(produces = APPLICATION_XML_VALUE)
	public CuisineXmlWrapper listXml() {
		return new CuisineXmlWrapper(cuisinesRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cuisine> search(@PathVariable Long id) {
		var cuisine = cuisinesRepository.findById(id);
		if(cuisine != null) {
			return ResponseEntity.ok(cuisine);
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
		if (currentCuisine != null) {
			BeanUtils.copyProperties(cuisine, currentCuisine, "id");
			cuisineRegistration.save(currentCuisine);
			return ResponseEntity.ok(currentCuisine);
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
}
