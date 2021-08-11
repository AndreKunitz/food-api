package me.andrekunitz.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.PermissionModel;
import me.andrekunitz.food.domain.model.Permission;

@Component
@RequiredArgsConstructor
public class PermissionModelAssembler {

	private final ModelMapper modelMapper;

	public PermissionModel toModel(Permission permission) {
		return modelMapper.map(permission, PermissionModel.class);
	}

	public List<PermissionModel> toCollectionModel(Collection<Permission> permissions) {
		return permissions.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
