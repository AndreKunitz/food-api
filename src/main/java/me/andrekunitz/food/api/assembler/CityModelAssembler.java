package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.CityModel;
import me.andrekunitz.food.domain.model.City;

@Component
@RequiredArgsConstructor
public class CityModelAssembler {

	private final ModelMapper modelMapper;

	public CityModel toModel(City city) {
		return modelMapper.map(city, CityModel.class);
	}

	public List<CityModel> toCollectionModel(List<City> cities) {
		return cities.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
