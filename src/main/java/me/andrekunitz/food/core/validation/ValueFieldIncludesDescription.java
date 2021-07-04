package me.andrekunitz.food.core.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValueFieldIncludesDescriptionValidator.class })
public @interface ValueFieldIncludesDescription {

	String message() default "invalid required description";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	String valueField();

	String descriptionField();

	String requiredDescription();

}
