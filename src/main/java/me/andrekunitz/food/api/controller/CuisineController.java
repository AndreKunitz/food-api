package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.CuisineInputDisassembler;
import me.andrekunitz.food.api.assembler.CuisineModelAssembler;
import me.andrekunitz.food.api.model.CuisineModel;
import me.andrekunitz.food.api.model.input.CuisineInput;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import me.andrekunitz.food.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping("/cuisines")
@RequiredArgsConstructor
public class CuisineController {

	private final CuisinesRepository cuisinesRepository;
	private final CuisineRegistrationService cuisineRegistrationService;
	private final CuisineModelAssembler cuisineModelAssembler;
	private final CuisineInputDisassembler cuisineInputDisassembler;

	@GetMapping
	public Page<CuisineModel> list(@PageableDefault(size = 5) Pageable pageable) {
		Page<Cuisine> cuisinesPage = cuisinesRepository.findAll(pageable);
		List<CuisineModel> cuisinesModel = cuisineModelAssembler.toCollectionModel(cuisinesPage.getContent());

		Page<CuisineModel> cuisinesModelPage = new PageImpl<>(cuisinesModel, pageable, cuisinesPage.getTotalElements());
		return cuisinesModelPage;
	}

	@GetMapping("/{id}")
	public CuisineModel search(@PathVariable Long id) {
		return cuisineModelAssembler.toModel(
				cuisineRegistrationService.fetchOrFail(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public CuisineModel add(@RequestBody @Valid CuisineInput cuisineInput) {
		var cuisine = cuisineInputDisassembler.toDomainObject(cuisineInput);

		return cuisineModelAssembler.toModel(
				cuisineRegistrationService.save(cuisine));
	}

	@PutMapping("/{id}")
	public CuisineModel update(@PathVariable Long id,
	                      @RequestBody @Valid CuisineInput cuisineInput) {
		var currentCuisine = cuisineRegistrationService.fetchOrFail(id);
		cuisineInputDisassembler.copyToDomainObject(cuisineInput, currentCuisine);

		return cuisineModelAssembler.toModel(
				cuisineRegistrationService.save(currentCuisine));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void remove(@PathVariable Long id) {
		cuisineRegistrationService.remove(id);
	}

	@GetMapping("/by-name")
	public List<Cuisine> cuisinesByName(String name) {
		return cuisinesRepository.findAllByNameContaining(name);
	}

	@GetMapping("/unique-by-name")
	public Optional<Cuisine> cuisineByName(String name) {
		return cuisinesRepository.findByName(name);
	}

	@GetMapping("/exists")
	public boolean cuisineExists(String name) {
		return cuisinesRepository.existsByName(name);
	}
}
