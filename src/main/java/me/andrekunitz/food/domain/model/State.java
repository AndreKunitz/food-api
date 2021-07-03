package me.andrekunitz.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.andrekunitz.food.Groups;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class State {

	@NotNull(groups = Groups.StateId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

}
