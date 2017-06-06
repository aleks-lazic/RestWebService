package ch.hes.openfoodRest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {

	// Client
	private static Client client;

	// Client Config
	private static ClientConfig config;

	// WebTarget
	private static WebTarget target;

	// Response
	private static String response;

	public static void main(String[] args) throws JSONException, JsonProcessingException {

		initializeConnection();

		// get all products
		System.out.println("Get All products");
		getAllProducts();
		
		// get all products sorted by quantity if parameter is true
		System.out.println("\nGet All products sorted by quantity");
		getAllProductsSortedByQuantity();

		// get product by name
		System.out.println("\nGet product by name");
		getProductByName("artichauts");

		// get product by name and quantity
		System.out.println("\nGet product by name and quantity");
		getByNameAndQuantity("palmier", 400);

		// delete product by id
		 System.out.println("\nDelete product by id");
		 deleteProductById("591c59933f8e2934d0c83be3");

		// CREATE FOOD
		Food food = new Food();
		food.setName("Cevapcici");
		food.setIngredients_translations("Viande, oignons, kajmak");
		food.setQuantity(200);
		food.setUnit("g");
		food.setPortion_quantity(20);
		food.setPortion_unit("g");
		Map<String, Nutrient> nutrients = new HashMap<String, Nutrient>();

		Nutrient glucides = new Nutrient();
		glucides.setName("glucides");
		glucides.setUnit("g");
		glucides.setPer_hundred(0.0);
		glucides.setPer_portion(0.0);
		glucides.setPer_day(0.0);

		Nutrient fiber = new Nutrient();
		fiber.setName("fiber");
		fiber.setUnit("g");
		fiber.setPer_hundred(0.0);
		fiber.setPer_portion(0.0);
		fiber.setPer_day(0.0);

		nutrients.put(glucides.getName(), glucides);
		nutrients.put(fiber.getName(), fiber);
		food.setNutrients(nutrients);

		createFood(food);

		// UPDATE PRODUCT
		System.out.println("\nUpdate a product");
		food.setName("Cevap");
		updateFood("592c272ff3a4432fc89b8148", food);

	}

	private static void initializeConnection() {
		config = new ClientConfig();

		client = ClientBuilder.newClient(config);

		target = client.target(getBaseURI());
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/").build();
	}

	private static void getAllProducts() {

		response = target.path("food").request().accept(MediaType.TEXT_PLAIN).header("Accept", "application/json")
				.get(String.class);

		System.out.println(response);

	}

	private static void getAllProductsSortedByQuantity() {
		response = target.path("food").path("sort").request().accept(MediaType.TEXT_PLAIN)
				.header("Accept", "application/json").get(String.class);

		System.out.println(response);

	}

	private static void getProductByName(String name) {
		response = target.path("food").queryParam("name", name).request().accept(MediaType.TEXT_PLAIN)
				.header("Accept", "application/json").get(String.class);

		System.out.println(response);

	}

	private static void getByNameAndQuantity(String name, int quantity) {
		response = target.path("food").queryParam("name", name).queryParam("quantity", quantity).request()
				.accept(MediaType.TEXT_PLAIN).header("Accept", "application/json").get(String.class);

		System.out.println(response);

	}

	private static void deleteProductById(String id) {
		Response response = target.path("food").path(id).request().accept(MediaType.TEXT_PLAIN)
				.header("Accept", "application/json").delete();

		System.out.println(response);

	}

	private static void createFood(Food food) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(food);

		// POST method
		Invocation.Builder invocationBuilder = target.path("food").request();
		invocationBuilder.post(Entity.entity(jsonInString, MediaType.APPLICATION_JSON));

	}

	private static void updateFood(String id, Food food) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(food);

		// PUT method
		Invocation.Builder invocationBuilder = target.path("food").path(id).request();
		invocationBuilder.put(Entity.entity(jsonInString, MediaType.APPLICATION_JSON));

	}
}
