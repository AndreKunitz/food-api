package me.andrekunitz.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.UserModel;
import me.andrekunitz.food.domain.model.User;

@Component
@RequiredArgsConstructor
public class UserModelAssembler {

	private final ModelMapper modelMapper;

	public UserModel toModel(User user) {
		return modelMapper.map(user, UserModel.class);
	}

	public List<UserModel> toCollectionModel(Collection<User> users) {
		return users.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
