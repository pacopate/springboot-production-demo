package impactotecnologico.challenge.pooling.car.utils;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;

import impactotecnologico.challenge.pooling.car.rest.exceptions.handlers.enums.ExceptionResponse;

public abstract class Verifications {

	/**
	 * Simple static method to help with validations process. It use Preconditions
	 * class (Guava) to reduce code
	 * 
	 * @param <T>
	 * @param object
	 * @throws IllegalArgumentException
	 */
	public static final <T> void checkIfNotNull(T object) throws IllegalArgumentException {
		Preconditions.checkNotNull(object);
		if (object instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) object)) {
			throw new IllegalArgumentException(ExceptionResponse.INVALID_CONTENT.message());
		}
	}
}
