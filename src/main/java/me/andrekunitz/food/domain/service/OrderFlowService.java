package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

	private final OrderIssuanceService orderIssuance;

	@Transactional
	public void confirm(Long orderId) {
		var order = orderIssuance.fetchOrFail(orderId);
		order.confirm();
	}

	@Transactional
	public void cancel(Long orderId) {
		var order = orderIssuance.fetchOrFail(orderId);
		order.cancel();
	}

	@Transactional
	public void deliver(Long orderId) {
		var order = orderIssuance.fetchOrFail(orderId);
		order.deliver();
	}
}
