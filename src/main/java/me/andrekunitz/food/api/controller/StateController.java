package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;
import me.andrekunitz.food.domain.service.StateRegistrationService;

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

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

	private final StateRepository stateRepository;
	private final StateRegistrationService stateRegistration;

	@GetMapping
	public List<State> list() {
		return stateRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<State> search(@PathVariable Long id) {
		var state = stateRepository.findById(id);

		if (state != null) {
			return ResponseEntity.ok(state);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public State add(@RequestBody State state) {
		return stateRegistration.save(state);
	}

	@PutMapping("/{id}")
	public ResponseEntity<State> update(@PathVariable Long id,
	                                    @RequestBody State state) {

		var currentState = stateRepository.findById(id);

		if (currentState != null) {
			BeanUtils.copyProperties(state, currentState, "id");
			currentState = stateRegistration.save(currentState);

			return ResponseEntity.ok(currentState);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {
		try {
			stateRegistration.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(CONFLICT)
					.body(e.getMessage());
		}
	}
}
