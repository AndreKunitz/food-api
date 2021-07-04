package me.andrekunitz.food.core.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@PositiveOrZero
public @interface DeliveryFee {

	@OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
	String message() default "{DeliveryFee.invalid}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
