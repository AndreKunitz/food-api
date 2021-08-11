package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.GroupModelAssembler;
import me.andrekunitz.food.api.model.GroupModel;
import me.andrekunitz.food.domain.service.UserRegistrationService;

@RestController
@RequestMapping("/users/{userId}/groups")
@RequiredArgsConstructor
public class UserGroupController {

	private final UserRegistrationService userRegistration;
	private final GroupModelAssembler groupModelAssembler;

	@GetMapping
	public List<GroupModel> list(@PathVariable Long userId) {
		var user = userRegistration.fetchOrFail(userId);

		return groupModelAssembler.toCollectionModel(user.getGroups());
	}

	@PutMapping("{groupId}")
	@ResponseStatus(NO_CONTENT)
	public void associate(@PathVariable Long userId,
						  @PathVariable Long groupId
	) {
		userRegistration.associateGroup(userId, groupId);
	}

	@DeleteMapping("{groupId}")
	@ResponseStatus(NO_CONTENT)
	public void disassociate(@PathVariable Long userId,
							 @PathVariable Long groupId
	) {
		userRegistration.disassociateGroup(userId, groupId);
	}
}
