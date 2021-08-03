package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
import me.andrekunitz.food.api.assembler.GroupInputDisassembler;
import me.andrekunitz.food.api.assembler.GroupModelAssembler;
import me.andrekunitz.food.api.model.GroupModel;
import me.andrekunitz.food.api.model.input.GroupInput;
import me.andrekunitz.food.domain.model.Group;
import me.andrekunitz.food.domain.repository.GroupRepository;
import me.andrekunitz.food.domain.service.GroupRegistrationService;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

	private final GroupRepository groupRepository;
	private final GroupRegistrationService groupRegistrationService;
	private final GroupModelAssembler groupModelAssembler;
	private final GroupInputDisassembler groupInputDisassembler;

	@GetMapping
	public List<GroupModel> list() {
		return groupModelAssembler.toCollectionModel(
				groupRepository.findAll());
	}

	@GetMapping("/{id}")
	public GroupModel search(@PathVariable Long id) {
		return groupModelAssembler.toModel(
				groupRegistrationService.fetchOrFail(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public GroupModel add(@RequestBody @Valid GroupInput groupInput) {
		Group group = groupInputDisassembler.toDomainObject(groupInput);
		group = groupRegistrationService.save(group);

		return groupModelAssembler.toModel(group);
	}

	@PutMapping("/{id}")
	public GroupModel update(@PathVariable Long id,
							 @RequestBody @Valid GroupInput groupInput
	) {
		Group currentGroup = groupRegistrationService.fetchOrFail(id);
		groupInputDisassembler.copyToDomainObject(groupInput, currentGroup);
		currentGroup = groupRegistrationService.save(currentGroup);

		return groupModelAssembler.toModel(currentGroup);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void remove(@PathVariable Long id) {
		groupRegistrationService.remove(id);
	}
}
