package me.andrekunitz.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.input.CuisineInput;
import me.andrekunitz.food.domain.model.Cuisine;

@Component
@RequiredArgsConstructor
public class CuisineInputDisassembler {

	private final ModelMapper modelMapper;

	public Cuisine toDomainObject(CuisineInput cuisineInput) {
		return modelMapper.map(cuisineInput, Cuisine.class);
	}

	public void copyToDomainObject(CuisineInput cuisineInput, Cuisine cuisine) {
		modelMapper.map(cuisineInput, cuisine);
	}
}
