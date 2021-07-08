package me.andrekunitz.food.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
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

	private OrderStatus status;

	@CreationTimestamp
	private OffsetDateTime registrationTimestamp;

	private OffsetDateTime confirmationTimestamp;
	private OffsetDateTime cancellationTimestamp;
	private OffsetDateTime deliveredTimestamp;

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
