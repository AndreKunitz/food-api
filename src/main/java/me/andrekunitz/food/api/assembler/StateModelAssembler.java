package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.StateModel;
import me.andrekunitz.food.domain.model.State;

@Component
@RequiredArgsConstructor
public class StateModelAssembler {

	private final ModelMapper modelMapper;

	public StateModel toModel(State state) {
		return modelMapper.map(state, StateModel.class);
	}

	public List<StateModel> toCollectionModel(List<State> states) {
		return states.stream()
				.map(state -> toModel(state))
				.collect(Collectors.toList());
	}
}
