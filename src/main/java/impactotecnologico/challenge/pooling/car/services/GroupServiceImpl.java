package impactotecnologico.challenge.pooling.car.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.GroupRepository;
import impactotecnologico.challenge.pooling.car.rest.exceptions.EntityNotFoundException;
import impactotecnologico.challenge.pooling.car.utils.Verifications;

@Component
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository journeyRepository;

	@Autowired
	private CarService carService;

	@Override
	public Optional<Group> registerGroupForJourney(Group group) {
		Verifications.checkIfNotNull(group);

		Optional<Group> found = this.journeyRepository.findOneByExternalId(group.getExternalId());

		if (found.isPresent()) {
			Group g = found.get();
			g.setPeople(group.getPeople());
			group = this.journeyRepository.save(g);
		} else {
			group = this.journeyRepository.save(group);
		}

		Verifications.checkIfNotNull(group);
		this.carService.assignGroupToCar(group);
		return Optional.of(group);
	}

	@Override
	public Optional<Boolean> unregisterGroupById(Integer id) {
		Verifications.checkIfNotNull(id);

		Optional<Group> found = this.journeyRepository.findOneByExternalId(id);

		if (found.isPresent()) {

			this.carService.unassignGroupToCar(found.get());
			this.journeyRepository.deleteOneByExternalId(id);
			return Optional.of(true);
		} else {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public Optional<Group> locateGroup(Integer id) throws EntityNotFoundException {

		Verifications.checkIfNotNull(id);

		Optional<Group> found = this.journeyRepository.findOneByExternalId(id);

		if (found.isPresent()) {
			return Optional.of(found.get());
		} else {
			throw new EntityNotFoundException();
		}

	}

}
