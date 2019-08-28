package impactotecnologico.challenge.pooling.car.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.repositories.CarRepository;
import impactotecnologico.challenge.pooling.car.rest.utils.Verifications;

@Component
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Override
	public Optional<List<Car>> refreshCarsAvailability(List<Car> cars) {

		Verifications.checkIsEmpty(cars);

		this.carRepository.deleteAll();
		if (this.carRepository.count() == 0) {
			return Optional.of(this.carRepository.saveAll(cars));
		} else {
			return Optional.<List<Car>>empty();
		}
	}

}
