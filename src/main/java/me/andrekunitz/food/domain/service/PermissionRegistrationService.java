package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.PermissionNotFoundException;
import me.andrekunitz.food.domain.model.Permission;
import me.andrekunitz.food.domain.repository.PermissionRepository;

@Service
@RequiredArgsConstructor
public class PermissionRegistrationService {

	private final PermissionRepository permissionRepository;

	public Permission fetchOrFail(Long id) {
		return permissionRepository.findById(id)
				.orElseThrow(() -> new PermissionNotFoundException(id));
	}

}
