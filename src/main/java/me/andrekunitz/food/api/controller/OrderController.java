package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.OrderInputDisassembler;
import me.andrekunitz.food.api.assembler.OrderModelAssembler;
import me.andrekunitz.food.api.assembler.OrderSummaryModelAssembler;
import me.andrekunitz.food.api.model.OrderModel;
import me.andrekunitz.food.api.model.input.OrderInput;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;
import me.andrekunitz.food.domain.model.User;
import me.andrekunitz.food.domain.repository.OrderRepository;
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
	public MappingJacksonValue list(@RequestParam(required = false) String fields) {
		var orders = orderRepository.findAll();
		var ordersSummaryModel = orderSummaryModelAssembler.toCollectionModel(orders);

		MappingJacksonValue ordersWrapper = new MappingJacksonValue(ordersSummaryModel);
		var filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());

		if (StringUtils.isNotBlank(fields)) {
			filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
		}

		ordersWrapper.setFilters(filterProvider);

		return ordersWrapper;
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
