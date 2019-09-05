package impactotecnologico.challenge.pooling.car.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used when an Entity wasn't found in the database
 * 
 * @author JoseJulian
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4620838821396092433L;
}
