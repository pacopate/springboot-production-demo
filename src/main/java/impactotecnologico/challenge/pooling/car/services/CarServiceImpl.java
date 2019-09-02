package impactotecnologico.challenge.pooling.car.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.CarRepository;
import impactotecnologico.challenge.pooling.car.utils.Verifications;

@Component
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Override
	public Optional<List<Car>> refreshCarsAvailability(List<Car> cars) {

		Verifications.checkIfNotNull(cars);

		this.carRepository.deleteAll();
		if (this.carRepository.count() == 0) {
			return Optional.of(this.carRepository.saveAll(cars.stream().map(c -> {
				c.setSeatsAvailables(c.getSeats());
				return c;
			}).collect(Collectors.toList())));
		} else {
			return Optional.<List<Car>>empty();
		}
	}

	@Override
	public Optional<Boolean> assignGroupToCar(Group toAssign) {
		Optional<Car> car = this.carRepository.findOneBySeatsAvailables(toAssign.getPeople());
		if (car.isPresent()) {
			Car c = car.get();
			c.setTravelers(toAssign);
			c.setSeatsAvailables(c.getSeatsAvailables() - toAssign.getPeople());
			this.carRepository.save(c);
			return Optional.of(true);
		} else {
			return Optional.of(false);
		}
	}

	@Override
	public Optional<Boolean> unassignGroupToCar(Group toUnassign) {

		Optional<Car> car = this.carRepository.findOneByTravelers(toUnassign);

		if (car.isPresent()) {
			Car c = car.get();
			c.setTravelers(null);
			c.setSeatsAvailables(c.getSeatsAvailables() + toUnassign.getPeople());
			this.carRepository.save(c);
			return Optional.of(true);
		} else {
			return Optional.of(false);
		}

	}

	@Override
	public Optional<Car> findCarByTravelers(Group travelers) {
		return this.carRepository.findOneByTravelers(travelers);
	}

}
