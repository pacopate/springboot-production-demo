package impactotecnologico.challenge.pooling.car.rest.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.rest.exceptions.ProcessingDataException;
import impactotecnologico.challenge.pooling.car.services.CarService;
import impactotecnologico.challenge.pooling.car.services.GroupService;
import impactotecnologico.challenge.pooling.car.utils.Verifications;

@RestController
public class JourneyRestController extends AbstractController {

	@Autowired
	CarService carService;

	@Autowired
	GroupService groupService;

	@PostMapping("/journey")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Group> registerGroup(@RequestBody Group group) throws IllegalArgumentException {
		Verifications.checkIfNotNull(group);

		if (group.getId() <= 0 || group.getPeople() <= 0) {
			throw new IllegalArgumentException();
		}

		Optional<Group> groupSaved = this.groupService.registerGroupForJourney(group);

		if (groupSaved.isPresent()) {
			Group data = groupSaved.get();
			return responseWithEntity(data);
		} else {
			throw new ProcessingDataException();
		}
	}

}
