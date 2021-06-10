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
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@JoinColumn(nullable = false)
	private String name;

	@JoinColumn(nullable = false)
	private String description;

	@JoinColumn(nullable = false)
	private BigDecimal price;

	@JoinColumn(nullable = false)
	private Boolean active;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Merchant merchant;

}
