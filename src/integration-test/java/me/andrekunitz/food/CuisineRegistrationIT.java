package me.andrekunitz.food;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import me.andrekunitz.food.util.ResourceUtils;

class CuisineRegistrationIT extends ApiAbstractIT {

    private static final int ID_CUISINE_NOT_EXISTS = 100;

    @Autowired
    private CuisinesRepository cuisinesRepository;

    private Cuisine americanCuisine;
    private int numberOfRegisteredCuisines;
    private String jsonChineseCuisine;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = "/cuisines";

        jsonChineseCuisine = ResourceUtils.getContentFromResource(
                "/json/valid/cuisine-chinese.json");

        this.databaseCleaner.clearTables();
        setUpData();
    }

    @Test
    public void shouldReturnStatus200_whenListingCuisines() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldContainCorrectNumberOfCuisines_whenListingCuisines() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(numberOfRegisteredCuisines));
    }

    @Test
    public void shouldReturnStatus201_whenRegisteringCuisine() {
        RestAssured
                .given()
                    .body(jsonChineseCuisine)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnCorrectResponseAndStatus200_whenFetchCuisine() {
        RestAssured
                .given()
                    .pathParam("cuisineId", americanCuisine.getId())
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cuisineId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", equalTo(americanCuisine.getName()));
    }

    @Test
    public void shouldReturnStatus404_whenCuisineNotExists() {
        RestAssured
                .given()
                    .pathParam("cuisineId", ID_CUISINE_NOT_EXISTS)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cuisineId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void setUpData() {
        var thaiCuisine = new Cuisine();
        thaiCuisine.setName("Thai");
        cuisinesRepository.save(thaiCuisine);

        americanCuisine = new Cuisine();
        americanCuisine.setName("American");
        cuisinesRepository.save(americanCuisine);

        numberOfRegisteredCuisines = (int) cuisinesRepository.count();
    }
}
