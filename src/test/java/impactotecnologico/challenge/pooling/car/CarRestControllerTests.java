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
import impactotecnologico.challenge.pooling.car.repositories.CarRepository;
import impactotecnologico.challenge.pooling.car.rest.controllers.CarRestController;
import impactotecnologico.challenge.pooling.car.rest.exceptions.ProcessingDataException;
import impactotecnologico.challenge.pooling.car.services.CarService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarRestControllerTests extends AbstractTest {

	@Mock
	private CarRepository carRepository;

	@Mock
	private CarService carServiceMock;

	@InjectMocks
	private CarRestController carRestController;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testHeaders() throws Exception {

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
	public void testStatusCode200() throws Exception {
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
	public void testStatusCode400() throws Exception {
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
	public void whenDeleteAllFails() {

		Mockito.doReturn(1L).when(carRepository).count();

		List<Car> cars = onlyOneCarGenerator();

		Mockito.doReturn(Optional.<List<Car>>empty()).when(carServiceMock).refreshCarsAvailability(cars);

		carRestController.update(cars);

	}

	@Test(expected = IllegalArgumentException.class)
	public void whenEmptyListReceived() {
		Mockito.doReturn(Optional.empty()).when(carServiceMock).refreshCarsAvailability(new LinkedList<Car>());
		carRestController.update(new LinkedList<Car>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenListWithZeroReceivedv1() {
		LinkedList<Car> cars = new LinkedList<Car>();
		cars.add(new Car(new ObjectId(), 1, 0));

		Mockito.doReturn(Optional.empty()).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenListWithZeroReceivedv2() {
		LinkedList<Car> cars = new LinkedList<Car>();
		cars.add(new Car(new ObjectId(), 0, 1));

		Mockito.doReturn(Optional.empty()).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);
	}

	@Test
	public void whenIsOk() {

		List<Car> cars = onlyOneCarGenerator();

		Mockito.doReturn(Optional.of(cars)).when(carServiceMock).refreshCarsAvailability(cars);

		try {
			ResponseEntity<List<Car>> response = carRestController.update(cars);

			List<Car> carsReturn = response.getBody();
			Mockito.verify(carServiceMock, Mockito.times(1)).refreshCarsAvailability(cars);
			Assert.assertEquals(cars, carsReturn);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = ProcessingDataException.class)
	public void whenSaveAllFailsv1() {

		List<Car> cars = onlyOneCarGenerator();

		Mockito.doReturn(Optional.<List<Car>>empty()).when(carServiceMock).refreshCarsAvailability(cars);

		carRestController.update(cars);

	}

	@Test(expected = ProcessingDataException.class)
	public void whenSaveAllFailsv2() {

		List<Car> cars = carsGenerator();

		Mockito.doReturn(Optional.of(new ArrayList<Car>())).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);

	}

	@Test(expected = ProcessingDataException.class)
	public void whenSaveAllFailsv3() {

		List<Car> cars = carsGenerator();

		List<Car> carsReturnedByService = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1));

		Mockito.doReturn(Optional.of(carsReturnedByService)).when(carServiceMock).refreshCarsAvailability(cars);
		carRestController.update(cars);

	}

	private List<Car> carsGenerator() {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1));
		cars.add(new Car(new ObjectId(), 2, 2));
		return cars;
	}

	private List<Car> onlyOneCarGenerator() {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(new ObjectId(), 1, 1));
		return cars;
	}

//	@Test
//	public void whenBodyIsEmpty() {
//
//		try {
//			when(carServiceMock.refreshCarsAvailability(Mockito.anyList())).thenReturn(Optional.<List<Car>>empty());
//			this.mockMvc.perform(
//					put("/cars")
//					.content(any(byte[].class))
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(content().string(""))
//				.andExpect(status().isBadRequest());
//
//		} catch (Exception e) {
//			Assert.state(false, "An error occurs: " + e.getMessage());
//			e.printStackTrace();
//
//		}
//
//	}
//
//	@Test
//	public void whenBodyIsOk() {
//
//		JSONArray data = new JSONArray();
//		data.put(new Car(new ObjectId(), 1, 1));
//
//		List<Car> cars = new ArrayList<Car>();
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		try {
//
//			cars = Arrays.asList(mapper.readValue(data.toString(), Car[].class));
//
//			when(carServiceMock.refreshCarsAvailability(cars)).thenReturn(Optional.of(cars));
////			this.mockMvc.perform(put("/cars").content(any(byte[].class)).contentType(MediaType.APPLICATION_JSON))
////					.andExpect(content().string("")).andExpect(status().isOk());
//		} catch (Exception e) {
//			Assert.state(false, "An error occurs: " + e.getMessage());
//			e.printStackTrace();
//
//		}
//
//	}

}
