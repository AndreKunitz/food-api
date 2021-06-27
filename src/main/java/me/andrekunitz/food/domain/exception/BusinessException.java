package me.andrekunitz.food.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(BAD_REQUEST)
public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}
}
