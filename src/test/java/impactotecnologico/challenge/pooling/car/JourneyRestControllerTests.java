package impactotecnologico.challenge.pooling.car;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

import impactotecnologico.challenge.pooling.car.models.Group;
import impactotecnologico.challenge.pooling.car.repositories.CarRepository;
import impactotecnologico.challenge.pooling.car.repositories.GroupRepository;
import impactotecnologico.challenge.pooling.car.rest.controllers.CarRestController;
import impactotecnologico.challenge.pooling.car.rest.controllers.JourneyRestController;
import impactotecnologico.challenge.pooling.car.rest.exceptions.ProcessingDataException;
import impactotecnologico.challenge.pooling.car.services.CarService;
import impactotecnologico.challenge.pooling.car.services.GroupService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JourneyRestControllerTests extends AbstractTest {

	@Mock
	private GroupService groupService;

	@Mock
	private GroupRepository groupRepository;

	@InjectMocks
	private JourneyRestController journeyRestController;

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

	@Test(expected = IllegalArgumentException.class)
	public void unregisterWhenInputIsNull() {
		journeyRestController.unregisterGroup(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void unregisterWhenInputIsZero() {

		journeyRestController.unregisterGroup(0);

	}

	@Test
	public void unregisterWhenIsOk() {

		Mockito.doReturn(Optional.of(true)).when(groupService).unregisterGroupById(1);

		journeyRestController.unregisterGroup(1);

	}

	@Test
	public void unregisterTestStatusCode404() throws Exception {
		String uri = "/dropoff";

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
						.content(generateEncoderParam(String.valueOf(Integer.MAX_VALUE))))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(404, status);

	}

	// Register Group Tests

	@Test(expected = NullPointerException.class)
	public void registerTestInvalidInputv1() {
		journeyRestController.registerGroup(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void registerTestInvalidInputv2() {
		journeyRestController.registerGroup(new Group());
	}

	@Test(expected = IllegalArgumentException.class)
	public void registerTestInvalidInputv3() {

		Group g = new Group(new ObjectId(), 0, 1, false);

		journeyRestController.registerGroup(g);
	}

	@Test(expected = IllegalArgumentException.class)
	public void registerTestInvalidInputv4() {

		Group g = new Group(new ObjectId(), 1, 0, false);

		journeyRestController.registerGroup(g);
	}

	@Test(expected = ProcessingDataException.class)
	public void registerTestWhenNotRegister() {

		Group valid = generateValidGroup();

		Mockito.doReturn(Optional.empty()).when(groupService).registerGroupForJourney(valid);

		journeyRestController.registerGroup(valid);

	}

	@Test
	public void registerTestWhenRegisterOk() {

		Group valid = generateValidGroup();
		Mockito.doReturn(Optional.of(valid)).when(groupService).registerGroupForJourney(valid);
		ResponseEntity<Group> response = journeyRestController.registerGroup(valid);

		Assert.assertEquals(valid, response.getBody());

	}

	@Test
	public void registerTestStatusCode400() throws Exception {
		String uri = "/journey";

		Group invalidGroup = new Group(new ObjectId(), 1, 0, false);

		String inputJson = super.mapToJson(invalidGroup);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(400, status);

	}

	@Test
	public void registerTestStatusCode200() throws Exception {
		String uri = "/journey";

		String inputJson = super.mapToJson(generateValidGroup());
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(200, status);

	}

	private Group generateValidGroup() {
		Group valid = new Group(new ObjectId(), 1, 5, false);
		return valid;
	}

	private String generateEncoderParam(String value) throws UnsupportedEncodingException {
		StringBuilder build = new StringBuilder();
		build.append(URLEncoder.encode("ID", StandardCharsets.UTF_8.name()).toString());
		build.append("=");
		build.append(URLEncoder.encode(value, StandardCharsets.UTF_8.name()).toString());
		return build.toString();
	}

}
