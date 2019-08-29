package impactotecnologico.challenge.pooling.car.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ProcessingDataException extends RuntimeException {
	private static final long serialVersionUID = 6474871969103275122L;

}
