package impactotecnologico.challenge.pooling.car.rest.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

	protected <T> ResponseEntity<List<T>> responseWithCollection(List<T> data) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Elements-Count", String.valueOf(data.size()));
		return new ResponseEntity<>(data, headers, HttpStatus.OK);
	}

	protected <T> ResponseEntity<T> responseWithEntity(T object) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		return new ResponseEntity<>(object, headers, HttpStatus.OK);
	}

}
