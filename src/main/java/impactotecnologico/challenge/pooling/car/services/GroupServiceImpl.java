package impactotecnologico.challenge.pooling.car.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.GroupRepository;
import impactotecnologico.challenge.pooling.car.utils.Verifications;

@Component
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository journeyRepository;

	@Override
	public Optional<Group> registerGroupForJourney(Group group) {
		Verifications.checkIfNotNull(group);
		group = this.journeyRepository.save(group);
		Verifications.checkIfNotNull(group);
		return Optional.of(group);
	}

}
