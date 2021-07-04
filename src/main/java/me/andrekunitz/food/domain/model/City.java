package me.andrekunitz.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.andrekunitz.food.core.validation.Groups;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;

import static javax.persistence.GenerationType.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class City {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@Valid
	@ConvertGroup(to = Groups.StateId.class)
	@ManyToOne
	@JoinColumn(nullable = false)
	private State state;

}
