package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.OrderSummaryModel;
import me.andrekunitz.food.domain.model.Order;

@Component
@RequiredArgsConstructor
public class OrderSummaryModelAssembler {

	private final ModelMapper modelMapper;

	public OrderSummaryModel toModel(Order order) {
		return modelMapper.map(order, OrderSummaryModel.class);
	}

	public List<OrderSummaryModel> toCollectionModel(List<Order> orders) {
		return orders.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
