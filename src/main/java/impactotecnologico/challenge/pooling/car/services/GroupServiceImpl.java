package impactotecnologico.challenge.pooling.car.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.GroupRepository;
import impactotecnologico.challenge.pooling.car.rest.exceptions.GroupNotFoundException;
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

	@Override
	public Optional<Boolean> unregisterGroupById(Integer id) {
		Verifications.checkIfNotNull(id);

		Optional<Group> found = this.journeyRepository.findOneByExternalId(id);

		if (found.isPresent()) {
			this.journeyRepository.deleteOneByExternalId(id);
			return Optional.of(true);
		} else {
			throw new GroupNotFoundException();
		}
	}

}
