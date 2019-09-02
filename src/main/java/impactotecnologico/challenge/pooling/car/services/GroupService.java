package impactotecnologico.challenge.pooling.car.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.rest.exceptions.EntityNotFoundException;

@Service
public interface GroupService {

	Optional<Group> registerGroupForJourney(Group group);

	Optional<Boolean> unregisterGroupById(Integer id);

	Optional<Group> locateGroup(Integer id) throws EntityNotFoundException;

}
