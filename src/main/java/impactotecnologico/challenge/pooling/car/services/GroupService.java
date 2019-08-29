package impactotecnologico.challenge.pooling.car.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import impactotecnologico.challenge.pooling.car.models.Group;

@Service
public interface GroupService {

	Optional<Group> registerGroupForJourney(Group group);

}
