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

		Verifications.checkIfNotNull(toAssign);

		Optional<Car> car = this.carRepository.findOneBySeatsAvailables(toAssign.getPeople());

		return createReturn(toAssign, car, false);
	}

	@Override
	public Optional<Boolean> unassignGroupToCar(Group toUnassign) {

		Verifications.checkIfNotNull(toUnassign);

		Optional<Car> car = this.carRepository.findOneByTravelers(toUnassign);

		return createReturn(toUnassign, car, true);

	}

	@Override
	public Optional<Car> findCarByTravelers(Group travelers) {

		Verifications.checkIfNotNull(travelers);

		return this.carRepository.findOneByTravelers(travelers);
	}

	/**
	 * Private method to unify the validations for assign and unassign cars
	 * 
	 * @param group the group to assign or unassign
	 * @param car   the car to manage seats
	 * @param sum   boolean to indicate if we want lock of unlock seats
	 * @return
	 */
	private Optional<Boolean> createReturn(Group group, Optional<Car> car, boolean sum) {
		if (car.isPresent()) {
			Car c = car.get();
			if (sum) {
				c.setSeatsAvailables(c.getSeatsAvailables() + group.getPeople());
				c.setTravelers(null);
			} else {
				c.setSeatsAvailables(c.getSeatsAvailables() - group.getPeople());
				c.setTravelers(group);
			}

			this.carRepository.save(c);
			return Optional.of(true);
		} else {
			return Optional.of(false);
		}
	}

}
