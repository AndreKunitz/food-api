package me.andrekunitz.food;

import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import me.andrekunitz.food.domain.repository.MerchantRepository;
import me.andrekunitz.food.util.ResourceUtils;

public class MerchantRegistrationIT extends ApiAbstractIT {

	private static final String BUSINESS_VIOLATION_PROBLEM_TYPE = "Business rule violation";

	private static final String INVALID_DATA_PROBLEM_TITLE = "Invalid data";

	private static final int ID_UNEXISITNG_MERCHANT = 100;

	@Autowired
	private CuisinesRepository cuisinesRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	private String jsonValidMerchant;
	private String jsonNoFreeDeliveryMerchant;
	private String jsonNoCuisineMerchant;
	private String jsonUnexistingCuisineMerchant;

	private Merchant burgerTopRestaurant;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/merchants";

		jsonValidMerchant = ResourceUtils.getContentFromResource(
				"/json/valid/merchant-new-york-barbacue.json");

		jsonNoFreeDeliveryMerchant = ResourceUtils.getContentFromResource(
				"/json/invalid/merchant-with-no-delivery-fee.json");

		jsonNoCuisineMerchant = ResourceUtils.getContentFromResource(
				"/json/invalid/merchant-with-no-cuisine.json");

		jsonUnexistingCuisineMerchant = ResourceUtils.getContentFromResource(
				"/json/invalid/merchant-with-unexisting-cuisine.json");

		databaseCleaner.clearTables();
		setUpData();
	}

	@Test
	public void shouldReturnStatus200_whenListingMerchants() {
		RestAssured
				.given()
					.accept(ContentType.JSON)
				.when()
					.get()
				.then()
					.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void shouldReturn201_whenRegisteringMerchant() {
		RestAssured
				.given()
					.body(jsonValidMerchant)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void shouldReturnStatus400_whenRegisteringMerchantWithoutDeliveryFee() {
		RestAssured
				.given()
					.body(jsonNoFreeDeliveryMerchant)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value())
					.body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
	}

	@Test
	public void shouldReturnStatus400_whenRegisteringMerchantWithoutCuisine() {
		RestAssured
				.given()
					.body(jsonNoCuisineMerchant)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value())
					.body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
	}

	@Test
	public void shouldReturnStatus400_whenRegisteringMerchantWithUnexistingCuisine() {
		RestAssured
				.given()
					.body(jsonUnexistingCuisineMerchant)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value())
					.body("title", equalTo(BUSINESS_VIOLATION_PROBLEM_TYPE));
	}

	@Test
	public void shouldReturnCorrectResponseAndStatus_whenFetchingExistingMerchant() {
		RestAssured
				.given()
					.pathParam("merchantId", burgerTopRestaurant.getId())
					.accept(ContentType.JSON)
				.when()
					.get("/{merchantId}")
				.then()
					.statusCode(HttpStatus.OK.value())
					.body("name", equalTo(burgerTopRestaurant.getName()));
	}

	@Test
	public void shouldReturnStatus404_whenFetchingUnexistingMerchant() {
		RestAssured
				.given()
					.pathParam("merchantId", ID_UNEXISITNG_MERCHANT)
					.accept(ContentType.JSON)
				.when()
					.get("/{merchantId}")
					.then()
				.statusCode(HttpStatus.NOT_FOUND.value());

	}

	private void setUpData() {
		var brazilianCuisine = new Cuisine();
		brazilianCuisine.setName("Brazilian");
		cuisinesRepository.save(brazilianCuisine);

		var americanCuisine = new Cuisine();
		americanCuisine.setName("American");
		cuisinesRepository.save(americanCuisine);

		burgerTopRestaurant = new Merchant();
		burgerTopRestaurant.setName("Burger Top");
		burgerTopRestaurant.setDeliveryFee(new BigDecimal(10));
		burgerTopRestaurant.setCuisine(americanCuisine);
		merchantRepository.save(burgerTopRestaurant);

		var brazilianFoodRestaurant = new Merchant();
		brazilianFoodRestaurant.setName("Comida Mineira");
		brazilianFoodRestaurant.setDeliveryFee(new BigDecimal(10));
		brazilianFoodRestaurant.setCuisine(brazilianCuisine);
		merchantRepository.save(brazilianFoodRestaurant);
	}
}
