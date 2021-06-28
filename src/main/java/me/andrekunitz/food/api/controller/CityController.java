package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

	@GetMapping
	public List<City> list() {
		return cityRepository.findAll();
	}

	@GetMapping("/{id}")
	public City search(@PathVariable Long id) {
		return cityRegistrationService.fetchOrFail(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public City add(@RequestBody City city) {
		try {
			return cityRegistrationService.save(city);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public City update(@PathVariable Long id,
	                   @RequestBody City city
	) {
		City currentCity = cityRegistrationService.fetchOrFail(id);
		BeanUtils.copyProperties(city, currentCity, "id");

		try {
			return cityRegistrationService.save(currentCity);
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
