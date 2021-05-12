package me.andrekunitz.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Permission {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

}
