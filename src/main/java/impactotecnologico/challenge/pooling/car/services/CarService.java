package impactotecnologico.challenge.pooling.car.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.models.Group;

@Service
public interface CarService {

	/**
	 * Used for endpoint PUT /cars
	 * 
	 * @param cars The list of cars to load
	 * @return a new list of Cars ready to journeys
	 */
	Optional<List<Car>> refreshCarsAvailability(List<Car> cars);

	/**
	 * Used for endpoint POST /locate
	 * 
	 * @param travelers A group of people requesting a car
	 * @return An optional of Car's group. Optional empty if this group haven't a
	 *         car yet
	 */
	Optional<Car> findCarByTravelers(Group travelers);

	/**
	 * Used for POST /journey
	 * 
	 * @param toAssign A group of people to assign to Car
	 * @return
	 */
	Optional<Boolean> assignGroupToCar(Group toAssign);

	/**
	 * Used for POST /dropoff
	 * 
	 * @param toUnassign a group to unassign
	 * @return Optional of True if the was removed. Optional of false if this group
	 *         haven't an assigned car
	 */
	Optional<Boolean> unassignGroupToCar(Group toUnassign);

}
