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

	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarServiceImpl carServiceMock;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void whenIsOk() {

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 0, new Group()));

		Mockito.doReturn(cars).when(carRepository).saveAll(cars);
		Optional<List<Car>> carsReturn = carServiceMock.refreshCarsAvailability(cars);

		Assert.assertEquals(Optional.of(cars), carsReturn);

	}

	@Test(expected = IllegalArgumentException.class)
	public void whenInputListIsEmpty() {
		List<Car> cars = new ArrayList<Car>();

		carServiceMock.refreshCarsAvailability(cars);

	}

	@Test
	public void whenDeleteAllFails() {

		Mockito.doReturn(1L).when(carRepository).count();

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 1, new Group()));

		Optional<List<Car>> carsReturn = carServiceMock.refreshCarsAvailability(cars);

		Assert.assertFalse(carsReturn.isPresent());

	}

}
