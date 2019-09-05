package impactotecnologico.challenge.pooling.car;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.CarRepository;
import impactotecnologico.challenge.pooling.car.rest.controllers.CarRestController;
import impactotecnologico.challenge.pooling.car.rest.exceptions.EntityNotFoundException;
import impactotecnologico.challenge.pooling.car.rest.exceptions.ProcessingDataException;
import impactotecnologico.challenge.pooling.car.services.CarService;
import impactotecnologico.challenge.pooling.car.services.GroupService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarRestControllerTests extends AbstractTest {

	@Mock
	private CarRepository carRepositoryMock;

	@Mock
	private CarService carServiceMock;

	@Mock
	private GroupService groupServiceMock;

	@InjectMocks
	private CarRestController carRestController;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testUpdateHeaders() throws Exception {

		String uri = "/cars";

		List<Car> cars = carsGenerator();

		String inputJson = super.mapToJson(cars);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String headerCount = "Elements-Count";
		Assert.assertTrue(mvcResult.getResponse().getHeaderNames().contains(headerCount));

		assertThat("Elements-Count OK!", Integer.valueOf(mvcResult.getResponse().getHeader(headerCount)),
				equalTo(cars.size()));

		String content = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(content, inputJson);
	}

	@Test
	public void testUpdateStatusCode200() throws Exception {
		String uri = "/cars";

		List<Car> cars = carsGenerator();

		String inputJson = super.mapToJson(cars);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(200, status);

	}

	@Test
	public void testUpdateStatusCode400() throws Exception {
		String uri = "/cars";

		List<Car> cars = new ArrayList<Car>();

		String inputJson = super.mapToJson(cars);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(400, status);

	}

	@Test(expected = ProcessingDataException.class)
	public void whenUpdateDeleteAllFails() {

		Mockito.doReturn(1L).when(carRepositoryMock).count();

		List<Car> cars = onlyOneCarGenerator();

		Mockito.doReturn(Optional.<List<Car>>empty()).when(carServiceMock).refreshCarsAvailability(cars);

		carRestController.update(cars);

	}

	@Test(expected = IllegalArgumentException.class)
	public void whenUpdateEmptyListReceived() {
		Mockito.doReturn(Optional.empty()).when(carServiceMock).refreshCarsAvailability(new LinkedList<Car>());
		carRestController.update(new LinkedList<Car>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenUpdateListWithZeroReceivedv1() {
		LinkedList<Car> cars = new LinkedList<Car>();
		cars.add(new Car(new ObjectId(), 1, 0, 0, new Group()));

		Mockito.doReturn(Optional.empty()).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenUpdateListWithZeroReceivedv2() {
		LinkedList<Car> cars = new LinkedList<Car>();
		cars.add(new Car(new ObjectId(), 0, 1, 0, new Group()));

		Mockito.doReturn(Optional.empty()).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);
	}

	@Test
	public void whenUpdateIsOk() {

		List<Car> cars = onlyOneCarGenerator();

		Mockito.doReturn(Optional.of(cars)).when(carServiceMock).refreshCarsAvailability(cars);

		try {
			ResponseEntity<List<Car>> response = carRestController.update(cars);

			Mockito.verify(carServiceMock, Mockito.times(1)).refreshCarsAvailability(cars);
			Assert.assertEquals(cars, response.getBody());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = ProcessingDataException.class)
	public void whenUpdateSaveAllFailsv1() {

		List<Car> cars = onlyOneCarGenerator();

		Mockito.doReturn(Optional.<List<Car>>empty()).when(carServiceMock).refreshCarsAvailability(cars);

		carRestController.update(cars);

	}

	@Test(expected = ProcessingDataException.class)
	public void whenUpdateSaveAllFailsv2() {

		List<Car> cars = carsGenerator();

		Mockito.doReturn(Optional.of(new ArrayList<Car>())).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);

	}

	@Test(expected = ProcessingDataException.class)
	public void whenUpdateSaveAllFailsv3() {

		List<Car> cars = carsGenerator();

		List<Car> carsReturnedByService = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 1, new Group()));

		Mockito.doReturn(Optional.of(carsReturnedByService)).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);

	}

	/* LOCATE TEST */

	@Test(expected = IllegalArgumentException.class)
	public void whenLocateInvalidInputReceivedv1() {

		carRestController.locate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenLocateInvalidInputReceivedv2() {

		carRestController.locate(0);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenLocateNotFoundAnyGroup() {

		Mockito.when(groupServiceMock.locateGroup(1)).thenReturn(Optional.empty());
		carRestController.locate(1);
	}

	@Test
	public void whenLocateCarFoundForThisGroup() {

		Group g = new Group();
		Car c = new Car();

		Mockito.doReturn(Optional.of(g)).when(groupServiceMock).locateGroup(1);

		Mockito.doReturn(Optional.of(c)).when(carServiceMock).findCarByTravelers(g);

		ResponseEntity<Car> car = carRestController.locate(1);

		Assert.assertEquals(car.getBody(), c);
	}

	@Test
	public void whenLocateCarNotFoundForThisGroup() {

		Group g = new Group();

		Mockito.doReturn(Optional.of(g)).when(groupServiceMock).locateGroup(1);

		Mockito.doReturn(Optional.empty()).when(carServiceMock).findCarByTravelers(g);

		ResponseEntity<Car> car = carRestController.locate(1);

		Assert.assertFalse(car.hasBody());
	}

	@Test
	public void testLocateStatusCode400() throws Exception {
		String uri = "/locate";

		String inputJson = super.mapToJson(0);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(400, status);

	}

	@Test
	public void testLocateStatusCode404() throws Exception {
		String uri = "/locate";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("ID", "9999999")).andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(404, status);

	}

	/* helpers */

	private List<Car> carsGenerator() {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 0, new Group()));
		cars.add(new Car(new ObjectId(), 2, 2, 0, new Group()));
		return cars;
	}

	private List<Car> onlyOneCarGenerator() {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1, 1, new Group()));
		return cars;
	}
}
