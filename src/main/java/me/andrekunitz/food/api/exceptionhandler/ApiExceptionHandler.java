package me.andrekunitz.food.api.exceptionhandler;

import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import me.andrekunitz.food.domain.exception.BusinessException;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_GENERIC_ERROR_FINAL_USER = "An unexpected internal system error has occurred. "
			+ "Please try again and if the problem persists, contact system administrator.";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.SYSTEM_ERROR;
		String detail = MSG_GENERIC_ERROR_FINAL_USER;

		ex.printStackTrace();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
	                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format("Resource %s, that you tried to access, does not exist.",
				ex.getRequestURL());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR_FINAL_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
	                                                    HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_PARAMETER;

		String detail = String.format("URL parameter '%s' received value '%s', "
						+ "which is invalid. Fix it and inform a value compatible with type %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR_FINAL_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
	                                                              HttpHeaders headers, HttpStatus status, WebRequest request) {

		var rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String detail = "Request body in invalid. Verify syntax error.";

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR_FINAL_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
	                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String detail = String.format("Property '%s' received value '%s', "
						+ "of invalid type. Fix it by informing a type compatible with type %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR_FINAL_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
	                                                              HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String detail = String.format("Property '%s' does not exists. "
				+ "Fix or remove this property and try again.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR_FINAL_USER)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

		HttpStatus status = NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();

		var problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {

		HttpStatus status = CONFLICT;
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		String detail = ex.getMessage();

		var problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

		HttpStatus status = BAD_REQUEST;
		ProblemType problemType = ProblemType.BUSINESS_ERROR;
		String detail = ex.getMessage();

		var problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
	                                                         HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder()
					.status(status.value())
					.title(status.getReasonPhrase())
					.timestamp(LocalDateTime.now())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.status(status.value())
					.title((String) body)
					.timestamp(LocalDateTime.now())
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
	                                                    ProblemType problemType, String detail) {

		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.timestamp(LocalDateTime.now())
				.detail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}
}
