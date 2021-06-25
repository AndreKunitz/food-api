package me.andrekunitz.food.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderLineItem {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private Integer quantity;
	private String observation;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;

}
