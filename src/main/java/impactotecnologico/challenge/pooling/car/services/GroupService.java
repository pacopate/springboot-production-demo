package impactotecnologico.challenge.pooling.car.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.rest.exceptions.EntityNotFoundException;

@Service
public interface GroupService {

	/**
	 * Used for endpoint POST /journey
	 * 
	 * @param group A group of people requests to perform a journey
	 * @return An Optional: empty if group wasn't registered. Optional<Group> if
	 *         registered was OK
	 */
	Optional<Group> registerGroupForJourney(Group group);

	/**
	 * Used for POST /dropoff
	 * 
	 * @param id An ID's group
	 * @return An Optional: empty or False if group wasn't unregistered or not
	 *         found. Optional<Boolean> True if group was unregistered
	 */
	Optional<Boolean> unregisterGroupById(Integer id);

	/**
	 * Used for endpoint POST /locate
	 * 
	 * @param id An ID's group
	 * @return An Optional: empty if group wasn't registered. Optional<Group> if
	 *         registered was OK
	 * @throws EntityNotFoundException When not group found
	 */
	Optional<Group> locateGroup(Integer id) throws EntityNotFoundException;

}
