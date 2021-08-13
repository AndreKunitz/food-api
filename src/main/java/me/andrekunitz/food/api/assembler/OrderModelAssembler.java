package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.OrderModel;
import me.andrekunitz.food.domain.model.Order;

@Component
@RequiredArgsConstructor
public class OrderModelAssembler {

	private final ModelMapper modelMapper;

	public OrderModel toModel(Order order) {
		return modelMapper.map(order, OrderModel.class);
	}

	public List<OrderModel> toCollectionModel(List<Order> orders) {
		return orders.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
