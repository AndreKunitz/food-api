package me.andrekunitz.food.domain.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public abstract class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message) {
		super(message);
	}
}
