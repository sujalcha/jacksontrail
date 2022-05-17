package jacksonTrail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FirstTrail {

	public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
		serialize();
		deserialize();
		serializeArray();
	}

	public static void serialize() throws StreamWriteException, DatabindException, IOException {
		ObjectMapper oMapper = new ObjectMapper();
		Car car = new Car("yellow", "renault");

		// to file
		oMapper.writeValue(new File("src/test/resources/car.json"), car);

		// to string
		String carAsString = oMapper.writeValueAsString(car);
		System.out.println(carAsString);
	}

	public static void deserialize() throws IOException {
		ObjectMapper oMapper = new ObjectMapper();

		// from string
		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Car car1 = oMapper.readValue(json, Car.class);
		System.out.println(car1.toString());

		// from files
		Car car2 = oMapper.readValue(new File("src/test/resources/car.json"), Car.class);
		System.out.println(car2.toString());

		// from url
		Car car3 = oMapper.readValue(new URL("file:src/test/resources/car.json"), Car.class);
		System.out.println(car3.toString());
	}

	//converting result into list
	public static void serializeArray() throws StreamWriteException, DatabindException, IOException {
		ObjectMapper oMapper = new ObjectMapper();
		String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
		List<Car> listCar = oMapper.readValue(jsonCarArray, new TypeReference<List<Car>>() {
		});
		System.out.println(listCar.toString());
		
	}
	
	public void configuringObjectMapper() throws JsonMappingException, JsonProcessingException {
		ObjectMapper oMapper = new ObjectMapper();
		oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		oMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		oMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		
		
		//converting result into array
		String jsonCarArray = 
				  "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
		oMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		Car[] cars = oMapper.readValue(jsonCarArray, Car[].class);
		System.out.println(cars.toString());
		
	}
}
