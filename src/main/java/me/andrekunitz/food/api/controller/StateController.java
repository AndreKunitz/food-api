package me.andrekunitz.food.api.controller;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.model.State;
import me.andrekunitz.food.domain.repository.StateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

	private final StateRepository stateRepository;

	@GetMapping
	public List<State> list() {
		return stateRepository.findAll();
	}
}
