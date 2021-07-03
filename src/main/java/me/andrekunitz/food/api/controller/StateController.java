package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;
import me.andrekunitz.food.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

	private final StateRepository stateRepository;
	private final StateRegistrationService stateRegistrationService;

	@GetMapping
	public List<State> list() {
		return stateRepository.findAll();
	}

	@GetMapping("/{id}")
	public State search(@PathVariable Long id) {
		return stateRegistrationService.fetchOrFail(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public State add(@RequestBody @Valid State state) {
		return stateRegistrationService.save(state);
	}

	@PutMapping("/{id}")
	public State update(@PathVariable Long id,
	                    @RequestBody @Valid State state
	) {
		var currentState = stateRegistrationService.fetchOrFail(id);
		BeanUtils.copyProperties(state, currentState, "id");

		return stateRegistrationService.save(currentState);
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable Long id) {
		stateRegistrationService.remove(id);
	}
}
