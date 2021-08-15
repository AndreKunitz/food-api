package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

	private final OrderIssuanceService orderIssuance;

	@Transactional
	public void confirm(String orderCode) {
		var order = orderIssuance.fetchOrFail(orderCode);
		order.confirm();
	}

	@Transactional
	public void cancel(String orderCode) {
		var order = orderIssuance.fetchOrFail(orderCode);
		order.cancel();
	}

	@Transactional
	public void deliver(String orderCode) {
		var order = orderIssuance.fetchOrFail(orderCode);
		order.deliver();
	}
}
