package impactotecnologico.challenge.pooling.car.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.models.Group;

@Service
public interface CarService {

	Optional<List<Car>> refreshCarsAvailability(List<Car> cars);

	Optional<Car> findCarByTravelers(Group travelers);

	Optional<Boolean> assignGroupToCar(Group toAssign);

	Optional<Boolean> unassignGroupToCar(Group toUnassign);

}
