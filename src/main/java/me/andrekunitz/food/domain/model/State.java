package me.andrekunitz.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class State {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

}
