package me.andrekunitz.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.input.OrderInput;
import me.andrekunitz.food.domain.model.Order;

@Component
@RequiredArgsConstructor
public class OrderInputDisassembler {

	private final ModelMapper modelMapper;

	public Order toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, Order.class);
	}

	public void copyToDomainObject(OrderInput orderInput, Order order) {
		modelMapper.map(orderInput, order);
	}
}
