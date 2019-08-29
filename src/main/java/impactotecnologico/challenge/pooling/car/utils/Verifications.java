package impactotecnologico.challenge.pooling.car.utils;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;

import impactotecnologico.challenge.pooling.car.rest.exceptions.handlers.enums.ExceptionResponse;

public abstract class Verifications {

	public static final <T> void checkIfNotNull(T object) throws IllegalArgumentException {
		Preconditions.checkNotNull(object);
		if (object instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) object)) {
			throw new IllegalArgumentException(ExceptionResponse.INVALID_CONTENT.message());
		} else {
			// boolean validate = Stream.of(object).allMatch(val -> val.getId() > 0 &&
			// val.getPeople() > 0);
		}
	}
}
