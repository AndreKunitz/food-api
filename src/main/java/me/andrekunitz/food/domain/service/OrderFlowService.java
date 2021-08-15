package me.andrekunitz.food.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.model.OrderStatus;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

	public static final String MSG_INVALID_ORDER_FLOW = "Status from order %d cannot be altered from %s to %s.";

	private final OrderIssuanceService orderIssuance;

	@Transactional
	public void confirm(Long orderId) {
		var order = orderIssuance.fetchOrFail(orderId);

		if (!order.getStatus().equals(OrderStatus.REGISTERED)) {
			throw new BusinessException(
					String.format(MSG_INVALID_ORDER_FLOW,
							order.getId(), order.getStatus().getDescription(),
							OrderStatus.CONFIRMED.getDescription()));
		}

		order.setStatus(OrderStatus.CONFIRMED);
		order.setConfirmationTimestamp(OffsetDateTime.now());
	}

	@Transactional
	public void cancel(Long orderId) {
		var order = orderIssuance.fetchOrFail(orderId);

		if (!order.getStatus().equals(OrderStatus.REGISTERED)) {
			throw new BusinessException(
					String.format(MSG_INVALID_ORDER_FLOW,
							order.getId(), order.getStatus().getDescription(),
							OrderStatus.CANCELED.getDescription()));
		}

		order.setStatus(OrderStatus.CANCELED);
		order.setCancellationTimestamp(OffsetDateTime.now());
	}

	@Transactional
	public void deliver(Long orderId) {
		var order = orderIssuance.fetchOrFail(orderId);

		if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
			throw new BusinessException(
					String.format(MSG_INVALID_ORDER_FLOW,
							order.getId(), order.getStatus().getDescription(),
							OrderStatus.DELIVERED.getDescription()));
		}

		order.setStatus(OrderStatus.DELIVERED);
		order.setCancellationTimestamp(OffsetDateTime.now());
	}


}
