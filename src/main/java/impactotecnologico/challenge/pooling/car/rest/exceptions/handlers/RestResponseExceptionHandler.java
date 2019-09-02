package impactotecnologico.challenge.pooling.car.rest.exceptions.handlers;

import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import impactotecnologico.challenge.pooling.car.rest.exceptions.EntityNotFoundException;
import impactotecnologico.challenge.pooling.car.rest.exceptions.ProcessingDataException;
import impactotecnologico.challenge.pooling.car.rest.exceptions.handlers.enums.ExceptionResponse;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private static final HttpHeaders JSON_HEADER = new HttpHeaders();

	public RestResponseExceptionHandler() {
		super();
		JSON_HEADER.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleBadRequest(final IllegalArgumentException ex, final WebRequest request) {
		return handleExceptionInternal(ex, createResponseMessage(ExceptionResponse.INVALID_CONTENT, null), JSON_HEADER,
				HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return handleExceptionInternal(ex, createResponseMessage(ExceptionResponse.NOT_READABLE, null), JSON_HEADER,
				HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final String bodyOfResponse = "handleMethodArgumentNotValid";
		return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
		return handleExceptionInternal(ex, createResponseMessage(ExceptionResponse.NOT_FOUND, null), JSON_HEADER,
				HttpStatus.NOT_FOUND, request);
	}

	// 409

	@ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
		final String bodyOfResponse = "I need to handleConflict";
		return handleExceptionInternal(ex, bodyOfResponse, JSON_HEADER, HttpStatus.CONFLICT, request);
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, createResponseMessage(ExceptionResponse.INVALID_CONTENT_TYPE, null),
				JSON_HEADER, HttpStatus.BAD_REQUEST, request);
	}

	// 500

	@ExceptionHandler({ NullPointerException.class, IllegalStateException.class, ProcessingDataException.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		return handleExceptionInternal(ex, createResponseMessage(ExceptionResponse.BIG_PROBLEM, ex.getMessage()),
				JSON_HEADER, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private String createResponseMessage(ExceptionResponse error, String detail) {
		JSONObject message = new JSONObject();

		if (detail == null)
			message.put("message", error.message());
		else {
			StringBuilder builder = new StringBuilder(error.message());
			builder.append(":");
			builder.append(" ");
			builder.append(detail);

			message.put("message", builder.toString());
		}

		message.put("code", error.code());
		return message.toString();
	}

}