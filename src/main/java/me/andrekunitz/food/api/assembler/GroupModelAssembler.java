package me.andrekunitz.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.GroupModel;
import me.andrekunitz.food.domain.model.Group;

@Component
@RequiredArgsConstructor
public class GroupModelAssembler {

	private final ModelMapper modelMapper;

	public GroupModel toModel(Group group) {
		return modelMapper.map(group, GroupModel.class);
	}

	public List<GroupModel> toCollectionModel(Collection<Group> groups) {
		return groups.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
