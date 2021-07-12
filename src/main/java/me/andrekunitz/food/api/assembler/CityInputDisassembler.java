package me.andrekunitz.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.input.CityInput;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.model.State;

@Component
@RequiredArgsConstructor
public class CityInputDisassembler {

	private final ModelMapper modelMapper;

	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInput cityInput, City city) {
		// To avoid org.hibernate.HibernateException: identifier of an instance of
		// me.andrekunitz.food.domain.model.State was altered from 1 to 2
		city.setState(new State());

		modelMapper.map(cityInput, city);
	}
}
