package impactotecnologico.challenge.pooling.car.rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.rest.exceptions.ProcessingDataException;
import impactotecnologico.challenge.pooling.car.services.CarService;
import impactotecnologico.challenge.pooling.car.utils.Verifications;

@RestController
@RequestMapping("/cars")
public class CarRestController extends AbstractController {

	@Autowired
	CarService carService;

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Car>> update(@RequestBody List<Car> cars) throws IllegalArgumentException {
		Verifications.checkIfNotNull(cars);

		cars.stream().anyMatch(any -> {
			if (any.getId() <= 0 || any.getSeats() <= 0) {
				throw new IllegalArgumentException();
			}
			return true;
		});

		Optional<List<Car>> carsUpdated = this.carService.refreshCarsAvailability(cars);
		if (carsUpdated.isPresent()) {
			List<Car> data = carsUpdated.get();
			if (CollectionUtils.isEmpty(data) || data.size() != cars.size()) {
				throw new ProcessingDataException();
			} else {
				return responseWithCollection(data);
			}
		} else {
			throw new ProcessingDataException();
		}
	}

}
