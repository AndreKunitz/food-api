package me.andrekunitz.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.andrekunitz.food.api.model.AddressModel;
import me.andrekunitz.food.api.model.input.OrderLineItemInput;
import me.andrekunitz.food.domain.model.Address;
import me.andrekunitz.food.domain.model.OrderLineItem;

@Component
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(OrderLineItemInput.class, OrderLineItem.class)
				.addMappings(mapper -> mapper.skip(OrderLineItem::setId));

		var addressToAddressModelTypeMap =
				modelMapper.createTypeMap(Address.class, AddressModel.class);

		addressToAddressModelTypeMap.<String>addMapping(
				src -> src.getCity().getState().getName(),
				(destination, value) -> destination.getCity().setState(value)
		);

		return modelMapper;
	}
}
