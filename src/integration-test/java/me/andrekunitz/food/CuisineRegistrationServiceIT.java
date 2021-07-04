package me.andrekunitz.food;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.andrekunitz.food.domain.exception.CuisineNotFoundException;
import me.andrekunitz.food.domain.exception.EntityInUseException;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.service.CuisineRegistrationService;

@SpringBootTest
class CuisineRegistrationServiceIT {

    @Autowired
    private CuisineRegistrationService cuisineRegistration;

    @Test
    @DisplayName("should assign id when registering cuisine with valid request")
    void shouldAssignId_whenRegisteringCuisineWithValidRequest() {
        var newCuisine = new Cuisine();
        newCuisine.setName("Chinese");

        newCuisine = cuisineRegistration.save(newCuisine);

        assertThat(newCuisine).isNotNull();
        assertThat(newCuisine.getId()).isNotNull();
    }

    @Test
    @DisplayName("should fail when registering new cuisine without name")
    public void shouldFail_whenRegisteringNewCuisineWithoutName() {
        var newCuisine = new Cuisine();
        newCuisine.setName(null);

        assertThrows(ConstraintViolationException.class, () -> {
            cuisineRegistration.save(newCuisine);
        });
    }

    @Test
    @DisplayName("should fail when deleting cuisine in use")
    public void shouldFail_whenDeletingCuisineInUse() {
        assertThrows(EntityInUseException.class, () -> {
            cuisineRegistration.remove(1L);
        });
    }

    @Test
    @DisplayName("should fail when deleting unexisting cuisine")
    public void shouldFail_whenDeletingUnexistingCuisine() {
        assertThrows(CuisineNotFoundException.class, () -> {
            cuisineRegistration.remove(100L);
        });
    }
}
