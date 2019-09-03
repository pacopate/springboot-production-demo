package impactotecnologico.challenge.pooling.car;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.GroupRepository;
import impactotecnologico.challenge.pooling.car.rest.exceptions.EntityNotFoundException;
import impactotecnologico.challenge.pooling.car.services.CarService;
import impactotecnologico.challenge.pooling.car.services.GroupServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupServiceTests extends AbstractTest {

	@Mock
	private GroupRepository groupRepository;

	@Mock
	private CarService carService;

	@InjectMocks
	private GroupServiceImpl groupServiceImpl;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void whenIsOkForJourney() {

		Group g = new Group(new ObjectId(), 1, 1);

		Mockito.doReturn(g).when(groupRepository).save(g);

		Mockito.doReturn(Optional.of(g)).when(groupRepository).findOneByExternalId(g.getExternalId());

		Optional<Group> groupReturned = groupServiceImpl.registerGroupForJourney(g);

		Assert.assertEquals(Optional.of(g), groupReturned);

	}

	@Test(expected = NullPointerException.class)
	public void whenInputIsAnEmptyGroupForJourney() {
		Group g = new Group();

		groupServiceImpl.registerGroupForJourney(g);

	}

	@Test
	public void whenLocateGroupIsOk() {

		Group g = new Group(new ObjectId(), 1, 1);

		Mockito.doReturn(Optional.of(g)).when(groupRepository).findOneByExternalId(g.getExternalId());

		Assert.assertTrue(groupServiceImpl.locateGroup(1).isPresent());
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenLocateGroupNotFound() {

		Mockito.when(groupRepository.findOneByExternalId(Mockito.anyInt())).thenReturn(Optional.empty());

		groupServiceImpl.locateGroup(Mockito.anyInt());
	}

	@Test
	public void unregisterOk() {

		Group g = new Group();
		Mockito.doReturn(Optional.of(g)).when(groupRepository).findOneByExternalId(1);

		Optional<Boolean> unregisterOk = groupServiceImpl.unregisterGroupById(1);

		Assert.assertTrue(unregisterOk.get());

	}

	@Test(expected = EntityNotFoundException.class)
	public void unregisterWhenNotFound() {

		Mockito.doReturn(Optional.empty()).when(groupRepository).findOneByExternalId(1);

		groupServiceImpl.unregisterGroupById(1);

	}

}
