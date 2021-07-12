package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

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
import me.andrekunitz.food.api.assembler.CityInputDisassembler;
import me.andrekunitz.food.api.assembler.CityModelAssembler;
import me.andrekunitz.food.api.model.CityModel;
import me.andrekunitz.food.api.model.input.CityInput;
import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.StateNotFoundException;
import me.andrekunitz.food.domain.model.City;
import me.andrekunitz.food.domain.repository.CityRepository;
import me.andrekunitz.food.domain.service.CityRegistrationService;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

	private final CityRepository cityRepository;
	private final CityRegistrationService cityRegistrationService;
	private final CityModelAssembler cityModelAssembler;
	private final CityInputDisassembler cityInputDisassembler;

	@GetMapping
	public List<CityModel> list() {
		return cityModelAssembler.toCollectionModel(
				cityRepository.findAll());
	}

	@GetMapping("/{id}")
	public CityModel search(@PathVariable Long id) {
		return cityModelAssembler.toModel(
				cityRegistrationService.fetchOrFail(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public CityModel add(@RequestBody @Valid CityInput cityInput) {
		try {
			var city = cityInputDisassembler.toDomainObject(cityInput);

			return cityModelAssembler.toModel(
					cityRegistrationService.save(city));
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public CityModel update(@PathVariable Long id,
	                   @RequestBody @Valid CityInput cityInput
	) {
		City currentCity = cityRegistrationService.fetchOrFail(id);

		cityInputDisassembler.copyToDomainObject(cityInput, currentCity);

		try {
			return cityModelAssembler.toModel(
					cityRegistrationService.save(currentCity));
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void remove(@PathVariable Long id) {
		cityRegistrationService.remove(id);
	}
}
