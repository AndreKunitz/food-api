package me.andrekunitz.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PaymentMethod {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String description;

}
