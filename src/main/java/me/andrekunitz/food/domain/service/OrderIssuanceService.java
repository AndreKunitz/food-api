package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.OrderNotFoundException;
import me.andrekunitz.food.domain.model.Order;
import me.andrekunitz.food.domain.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderIssuanceService {

	private final OrderRepository orderRepository;
	private final CityRegistrationService cityRegistration;
	private final UserRegistrationService userRegistration;
	private final MerchantRegistrationService merchantRegistration;
	private final PaymentMethodRegistrationService paymentMethodRegistration;
	private final ProductRegistrationService productRegistration;

	@Transactional
	public Order issue(Order order) {
		validateOrder(order);
		validateItems(order);

		order.setDeliveryFee(order.getMerchant().getDeliveryFee());
		order.calculateOrderTotalPrice();

		return orderRepository.save(order);
	}

	private void validateOrder(Order order) {
		var city = cityRegistration.fetchOrFail(order.getDeliveryAddress().getCity().getId());
		var client = userRegistration.fetchOrFail(order.getClient().getId());
		var merchant = merchantRegistration.fetchOrFail(order.getMerchant().getId());
		var paymentMethod = paymentMethodRegistration.fetchOrFail(order.getPaymentMethod().getId());

		order.getDeliveryAddress().setCity(city);
		order.setClient(client);
		order.setMerchant(merchant);
		order.setPaymentMethod(paymentMethod);

		if (merchant.notAcceptsPaymentMethod(paymentMethod)) {
			throw new BusinessException(String.format("Payment method '%s' is not accepted by this merchant.",
					paymentMethod.getDescription()));
		}
	}

	private void validateItems(Order order) {
		order.getItems().forEach(item -> {
			var product = productRegistration.fetchOrFail(
					order.getMerchant().getId(), item.getProduct().getId());

			item.setOrder(order);
			item.setProduct(product);
			item.setUnitPrice(product.getPrice());
		});
	}

	public Order fetchOrFail(String orderCode) {
		return orderRepository.findByCode(orderCode)
				.orElseThrow(() -> new OrderNotFoundException(orderCode));
	}
}
