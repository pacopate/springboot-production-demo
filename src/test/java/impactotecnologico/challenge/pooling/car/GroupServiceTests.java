package impactotecnologico.challenge.pooling.car;

import java.util.ArrayList;
import java.util.List;
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

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.CarRepository;
import impactotecnologico.challenge.pooling.car.repositories.GroupRepository;
import impactotecnologico.challenge.pooling.car.services.CarServiceImpl;
import impactotecnologico.challenge.pooling.car.services.GroupServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupServiceTests extends AbstractTest {

	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarServiceImpl carServiceMock;

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

		Group g = new Group(new ObjectId(), 1, 1);

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
	public void whenDeleteAllFails() {

		Mockito.doReturn(1L).when(carRepository).count();

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1));

		Optional<List<Car>> carsReturn = carServiceMock.refreshCarsAvailability(cars);

		Assert.assertFalse(carsReturn.isPresent());

	}

}
