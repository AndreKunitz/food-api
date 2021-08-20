package me.andrekunitz.food.api.controller;

import static me.andrekunitz.food.infrastructure.repository.specification.OrderSpecification.*;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.OrderInputDisassembler;
import me.andrekunitz.food.api.assembler.OrderModelAssembler;
import me.andrekunitz.food.api.assembler.OrderSummaryModelAssembler;
import me.andrekunitz.food.api.model.OrderModel;
import me.andrekunitz.food.api.model.OrderSummaryModel;
import me.andrekunitz.food.api.model.input.OrderInput;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.Order;
import me.andrekunitz.food.domain.model.User;
import me.andrekunitz.food.domain.repository.OrderRepository;
import me.andrekunitz.food.domain.repository.filter.OrderFilter;
import me.andrekunitz.food.domain.service.OrderIssuanceService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderRepository orderRepository;
	private final OrderIssuanceService orderIssuance;
	private final OrderModelAssembler orderModelAssembler;
	private final OrderSummaryModelAssembler orderSummaryModelAssembler;
	private final OrderInputDisassembler orderInputDisassembler;

	@GetMapping
	public Page<OrderSummaryModel> search(OrderFilter filter, Pageable pageable) {
		Page<Order> ordersPage = orderRepository.findAll(withFilter(filter), pageable);
		List<OrderSummaryModel> orderSummariesModel = orderSummaryModelAssembler
				.toCollectionModel(ordersPage.getContent());
		Page<OrderSummaryModel> orderSummariesModelPage = new PageImpl<>(orderSummariesModel,
				pageable, ordersPage.getTotalElements());

		return orderSummariesModelPage;
	}

	@GetMapping("{orderCode}")
	public OrderModel search(@PathVariable String orderCode) {
		return orderModelAssembler.toModel(
				orderIssuance.fetchOrFail(orderCode));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public OrderModel add(@Valid @RequestBody OrderInput orderInput) {
		try {
			var newOrder = orderInputDisassembler.toDomainObject(orderInput);

			// TODO: authenticate user
			newOrder.setClient(new User());
			newOrder.getClient().setId(1L);

			newOrder = orderIssuance.issue(newOrder);

			return orderModelAssembler.toModel(newOrder);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
