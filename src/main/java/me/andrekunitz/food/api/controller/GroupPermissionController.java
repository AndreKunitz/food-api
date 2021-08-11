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
import me.andrekunitz.food.api.assembler.PermissionModelAssembler;
import me.andrekunitz.food.api.model.PermissionModel;
import me.andrekunitz.food.domain.service.GroupRegistrationService;

@RestController
@RequestMapping("/groups/{groupId}/permissions")
@RequiredArgsConstructor
public class GroupPermissionController {

	private final GroupRegistrationService groupRegistration;
	private final PermissionModelAssembler permissionModelAssembler;

	@GetMapping
	public List<PermissionModel> list(@PathVariable Long groupId) {
		var group = groupRegistration.fetchOrFail(groupId);

		return permissionModelAssembler.toCollectionModel(
				group.getPermissions());
	}

	@PutMapping("{permissionId}")
	@ResponseStatus(NO_CONTENT)
	public void associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupRegistration.associatePermission(groupId, permissionId);
	}

	@DeleteMapping("{permissionId}")
	@ResponseStatus(NO_CONTENT)
	public void disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupRegistration.disassociatePermission(groupId, permissionId);
	}

}
