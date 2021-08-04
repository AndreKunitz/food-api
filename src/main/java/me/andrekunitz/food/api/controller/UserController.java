package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.UserInputDisassembler;
import me.andrekunitz.food.api.assembler.UserModelAssembler;
import me.andrekunitz.food.api.model.UserModel;
import me.andrekunitz.food.api.model.input.PasswordInput;
import me.andrekunitz.food.api.model.input.UserInput;
import me.andrekunitz.food.api.model.input.UserWithPasswordInput;
import me.andrekunitz.food.domain.repository.UserRepository;
import me.andrekunitz.food.domain.service.UserRegistrationService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;
	private final UserRegistrationService userRegistrationService;
	private final UserModelAssembler userModelAssembler;
	private final UserInputDisassembler userInputDisassembler;

	@GetMapping
	public List<UserModel> list() {
		return userModelAssembler.toCollectionModel(
				userRepository.findAll());
	}

	@GetMapping("/{id}")
	public UserModel search(@PathVariable Long id) {
		return userModelAssembler.toModel(
				userRegistrationService.fetchOrFail(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public UserModel add(@RequestBody @Valid UserWithPasswordInput userInput) {
		var user = userInputDisassembler.toDomainObject(userInput);
		user = userRegistrationService.save(user);

		return userModelAssembler.toModel(user);
	}

	@PutMapping("/{id}")
	public UserModel update(@PathVariable Long id,
							@RequestBody @Valid UserInput userInput
	) {
		var currentUser = userRegistrationService.fetchOrFail(id);
		userInputDisassembler.copyToDomainObject(userInput, currentUser);
		currentUser = userRegistrationService.save(currentUser);

		return userModelAssembler.toModel(currentUser);
	}

	@PutMapping("/{id}/password")
	@ResponseStatus(NO_CONTENT)
	public void changePassword(@PathVariable Long id,
							   @RequestBody @Valid PasswordInput passwordInput
	) {
		userRegistrationService.changePassword(
				id, passwordInput.getCurrentPassword(), passwordInput.getNewPassword());
	}
}
