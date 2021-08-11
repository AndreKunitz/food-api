package me.andrekunitz.food.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.GroupNotFoundException;
import me.andrekunitz.food.domain.model.Group;
import me.andrekunitz.food.domain.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupRegistrationService {

	public static final String MSG_GROUP_IN_USE = "Group with id %d is in use and cannot be removed";

	private final GroupRepository groupRepository;
	private final PermissionRegistrationService permissionRegistration;

	@Transactional
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Transactional
	public void remove(Long id) {
		try {
			groupRepository.deleteById(id);
			groupRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GroupNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_GROUP_IN_USE, id));
		}
	}

	public Group fetchOrFail(Long id) {
		return groupRepository.findById(id)
				.orElseThrow(() -> new GroupNotFoundException(id));
	}

	@Transactional
	public void disassociatePermission(Long groupId, Long permissionId) {
		var group = fetchOrFail(groupId);
		var permission = permissionRegistration.fetchOrFail(permissionId);

		group.removePermission(permission);
	}

	@Transactional
	public void associatePermission(Long groupId, Long permissionId) {
		var group = fetchOrFail(groupId);
		var permission = permissionRegistration.fetchOrFail(permissionId);

		group.addPermission(permission);
	}

}
