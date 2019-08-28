package impactotecnologico.challenge.pooling.car.rest.utils;

import java.util.List;

import com.google.common.base.Preconditions;

import impactotecnologico.challenge.pooling.car.rest.exceptions.handlers.enums.ExceptionResponse;

public abstract class Verifications {

	public static final <T> void checkIsEmpty(T object) throws IllegalArgumentException {
		Preconditions.checkNotNull(object);
		List<?> elements = (List<?>) object;
		if (elements.isEmpty()) {
			throw new IllegalArgumentException(ExceptionResponse.INVALID_CONTENT.message());
		}
	}

}
