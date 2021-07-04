package me.andrekunitz.food.core.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueFieldIncludesDescriptionValidator implements ConstraintValidator<ValueFieldIncludesDescription, Object> {

	private String valueField;
	private String descriptionField;
	private String requiredDescription;

	@Override
	public void initialize(ValueFieldIncludesDescription constraintAnnotation) {
		this.valueField = constraintAnnotation.valueField();
		this.descriptionField = constraintAnnotation.descriptionField();
		this.requiredDescription = constraintAnnotation.requiredDescription();
	}

	@Override
	public boolean isValid(Object validatedObject, ConstraintValidatorContext context) {
		boolean valid = true;

		try {
			BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(validatedObject.getClass(), valueField)
					.getReadMethod().invoke(validatedObject);

			String description = (String) BeanUtils.getPropertyDescriptor(validatedObject.getClass(), descriptionField)
					.getReadMethod().invoke(validatedObject);

			if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
				valid = description.toLowerCase().contains(this.requiredDescription.toLowerCase());
			}

			return valid;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

}
