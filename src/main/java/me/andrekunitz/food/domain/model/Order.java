package me.andrekunitz.food.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
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

	private OrderStatus status;

	@CreationTimestamp
	private LocalDateTime registrationTimestamp;

	private LocalDateTime confirmationTimestamp;
	private LocalDateTime cancellationTimestamp;
	private LocalDateTime deliveredTimestamp;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PaymentMethod paymentMethods;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Merchant merchant;

	@ManyToOne
	@JoinColumn(name = "user_client_id", nullable = false)
	private User client;

	@OneToMany(mappedBy = "order")
	private List<OrderLineItem> items = new ArrayList<>();

}
