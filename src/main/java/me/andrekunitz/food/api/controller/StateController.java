package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import javax.validation.Valid;

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
import me.andrekunitz.food.api.assembler.StateInputDisassembler;
import me.andrekunitz.food.api.assembler.StateModelAssembler;
import me.andrekunitz.food.api.model.StateModel;
import me.andrekunitz.food.api.model.input.StateInput;
import me.andrekunitz.food.domain.repository.StateRepository;
import me.andrekunitz.food.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

	private final StateRepository stateRepository;
	private final StateRegistrationService stateRegistrationService;
	private final StateModelAssembler stateModelAssembler;
	private final StateInputDisassembler stateInputDisassembler;

	@GetMapping
	public List<StateModel> list() {
		return stateModelAssembler.toCollectionModel(
				stateRepository.findAll());
	}

	@GetMapping("/{id}")
	public StateModel search(@PathVariable Long id) {
		return stateModelAssembler.toModel(
				stateRegistrationService.fetchOrFail(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public StateModel add(@RequestBody @Valid StateInput stateInput) {
		var state = stateInputDisassembler.toDomainObject(stateInput);

		return stateModelAssembler.toModel(
				stateRegistrationService.save(state));
	}

	@PutMapping("/{id}")
	public StateModel update(@PathVariable Long id,
	                    @RequestBody @Valid StateInput stateInput
	) {
		var currentState = stateRegistrationService.fetchOrFail(id);

		stateInputDisassembler.copyToDomainObject(stateInput, currentState);

		return stateModelAssembler.toModel(
				stateRegistrationService.save(currentState));
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable Long id) {
		stateRegistrationService.remove(id);
	}
}
