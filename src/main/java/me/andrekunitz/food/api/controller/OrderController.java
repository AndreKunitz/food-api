package me.andrekunitz.food.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.OrderModelAssembler;
import me.andrekunitz.food.api.model.OrderModel;
import me.andrekunitz.food.domain.repository.OrderRepository;
import me.andrekunitz.food.domain.service.OrderIssuanceService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderRepository orderRepository;
	private final OrderIssuanceService orderIssuanceService;
	private final OrderModelAssembler orderModelAssembler;

	@GetMapping
	public List<OrderModel> list() {
		return orderModelAssembler.toCollectionModel(
				orderRepository.findAll());
	}

	@GetMapping("{id}")
	public OrderModel search(@PathVariable Long id) {
		return orderModelAssembler.toModel(
				orderIssuanceService.fetchOrFail(id));
	}
}
