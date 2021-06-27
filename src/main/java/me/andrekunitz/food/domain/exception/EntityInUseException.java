package me.andrekunitz.food.domain.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(CONFLICT)
public class EntityInUseException extends RuntimeException {

	public EntityInUseException(String message) {
		super(message);
	}
}
