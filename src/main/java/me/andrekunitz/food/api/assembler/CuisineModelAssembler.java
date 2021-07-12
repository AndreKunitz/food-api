package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.CuisineModel;
import me.andrekunitz.food.domain.model.Cuisine;

@Component
@RequiredArgsConstructor
public class CuisineModelAssembler {

	private final ModelMapper modelMapper;

	public CuisineModel toModel(Cuisine cuisine) {
		return modelMapper.map(cuisine, CuisineModel.class);
	}

	public List<CuisineModel> toCollectionModel(List<Cuisine> cuisines) {
		return cuisines.stream()
				.map(cuisine -> toModel(cuisine))
				.collect(Collectors.toList());
	}
}
