package me.andrekunitz.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.andrekunitz.food.api.model.AddressModel;
import me.andrekunitz.food.domain.model.Address;

@Component
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		var addressToAddressModelTypeMap =
				modelMapper.createTypeMap(Address.class, AddressModel.class);

		addressToAddressModelTypeMap.<String>addMapping(
				src -> src.getCity().getState().getName(),
				(destination, value) -> destination.getCity().setState(value)
		);

		return modelMapper;
	}
}
