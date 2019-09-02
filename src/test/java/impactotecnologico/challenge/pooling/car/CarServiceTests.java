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
import impactotecnologico.challenge.pooling.car.services.CarServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarServiceTests extends AbstractTest {

	private static final int SEATS = 5;

	@Mock
	private CarRepository carRepositoryMock;

	@InjectMocks
	private CarServiceImpl carService;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void whenRefreshIsOk() {

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 0, new Group()));

		Mockito.doReturn(cars).when(carRepositoryMock).saveAll(cars);
		Optional<List<Car>> carsReturn = carService.refreshCarsAvailability(cars);

		Assert.assertEquals(Optional.of(cars), carsReturn);

	}

	@Test(expected = IllegalArgumentException.class)
	public void whenInputListForRefreshIsEmpty() {
		List<Car> cars = new ArrayList<Car>();

		carService.refreshCarsAvailability(cars);

	}

	@Test
	public void whenDeleteAllFails() {

		Mockito.doReturn(1L).when(carRepositoryMock).count();

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 1, new Group()));

		Optional<List<Car>> carsReturn = carService.refreshCarsAvailability(cars);

		Assert.assertFalse(carsReturn.isPresent());

	}

	@Test
	public void whenACarHasThatTravelers() {
		Group travelers = new Group();

		Mockito.doReturn(Optional.of(new Car())).when(carRepositoryMock).findOneByTravelers(travelers);

		Assert.assertTrue(carService.findCarByTravelers(travelers).isPresent());
	}

	@Test
	public void whenAnyCarHasThatTravelers() {
		Group travelers = new Group();

		Mockito.doReturn(Optional.empty()).when(carRepositoryMock).findOneByTravelers(travelers);

		Assert.assertFalse(carService.findCarByTravelers(travelers).isPresent());
	}

	@Test
	public void whenUnavailableSeatsForAssigningCar() {

		Group g = new Group(null, 0, SEATS);

		Mockito.doReturn(Optional.empty()).when(carRepositoryMock).findOneBySeatsAvailables(g.getPeople());

		Assert.assertFalse(carService.assignGroupToCar(g).get());
	}

	@Test
	public void whenTheAreAvailableSeatsForAssigningCar() {

		Car c = new Car();
		Group g = new Group(null, 0, SEATS);

		Mockito.doReturn(Optional.of(c)).when(carRepositoryMock).findOneBySeatsAvailables(g.getPeople());

		Assert.assertTrue(carService.assignGroupToCar(g).get());
	}

	@Test
	public void whenGoupIsUnassignToCar() {
		Car c = new Car();
		Group g = new Group(null, 0, SEATS);

		Mockito.doReturn(Optional.of(c)).when(carRepositoryMock).findOneByTravelers(g);

		Assert.assertTrue(carService.unassignGroupToCar(g).get());

	}

}
