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
import impactotecnologico.challenge.pooling.car.services.GroupServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupServiceTests extends AbstractTest {

	@Mock
	private GroupRepository groupRepository;

	@InjectMocks
	private GroupServiceImpl groupServiceImpl;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void whenIsOk() {

		Group g = new Group(new ObjectId(), 1, 1, true);

		Mockito.doReturn(g).when(groupRepository).save(g);
		Optional<Group> groupReturned = groupServiceImpl.registerGroupForJourney(g);

		Assert.assertEquals(Optional.of(g), groupReturned);

	}

	@Test(expected = NullPointerException.class)
	public void whenInputIsAnEmptyGroup() {
		Group g = new Group();

		groupServiceImpl.registerGroupForJourney(g);

	}

	@Test
	public void unregisterOk() {

		Mockito.doReturn(Optional.of(Group.class)).when(groupRepository).findOneByExternalId(1);

		Optional<Boolean> unregisterOk = groupServiceImpl.unregisterGroupById(1);

		Assert.assertTrue(unregisterOk.get());

	}

	@Test(expected = EntityNotFoundException.class)
	public void unregisterWhenNotFound() {

		Mockito.doReturn(Optional.empty()).when(groupRepository).findOneByExternalId(1);

		groupServiceImpl.unregisterGroupById(1);

	}

}
