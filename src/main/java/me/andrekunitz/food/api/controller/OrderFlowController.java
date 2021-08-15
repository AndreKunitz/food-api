package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.service.OrderFlowService;

@RestController
@RequestMapping("/orders/{orderId}")
@RequiredArgsConstructor
public class OrderFlowController {

	private final OrderFlowService orderFlow;

	@PutMapping("/confirmation")
	@ResponseStatus(NO_CONTENT)
	public void confirm(@PathVariable Long orderId) {
		orderFlow.confirm(orderId);
	}

	@PutMapping("/cancellation")
	@ResponseStatus(NO_CONTENT)
	public void cancel(@PathVariable Long orderId) {
		orderFlow.cancel(orderId);
	}

	@PutMapping("/delivery")
	@ResponseStatus(NO_CONTENT)
	public void deliver(@PathVariable Long orderId) {
		orderFlow.deliver(orderId);
	}
}
