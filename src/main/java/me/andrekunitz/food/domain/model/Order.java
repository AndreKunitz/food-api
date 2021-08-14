package me.andrekunitz.food.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="order$")
public class Order {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private BigDecimal subtotal;
	private BigDecimal deliveryFee;
	private BigDecimal totalPrice;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.REGISTERED;

	@CreationTimestamp
	private OffsetDateTime registrationTimestamp;

	private OffsetDateTime confirmationTimestamp;
	private OffsetDateTime cancellationTimestamp;
	private OffsetDateTime deliveredTimestamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private PaymentMethod paymentMethod;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Merchant merchant;

	@ManyToOne
	@JoinColumn(name = "user_client_id", nullable = false)
	private User client;

	@OneToMany(mappedBy = "order")
	private List<OrderLineItem> items = new ArrayList<>();

	public void calculateTotalPrice() {
		this.subtotal = getItems().stream()
				.map(item -> item.getTotalPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.totalPrice = this.subtotal.add(this.deliveryFee);
	}

	public void setDeliveryFee() {
		setDeliveryFee(getMerchant().getDeliveryFee());
	}

	public void assignOrderToItems() {
		getItems().forEach(item -> item.setOrder(this));
	}
}
