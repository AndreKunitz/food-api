package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.OrderNotFoundException;
import me.andrekunitz.food.domain.model.Order;
import me.andrekunitz.food.domain.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderIssuanceService {

	public final OrderRepository orderRepository;

	public Order fetchOrFail(Long id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException(id));
	}
}
