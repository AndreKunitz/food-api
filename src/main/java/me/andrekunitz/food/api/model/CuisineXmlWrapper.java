package me.andrekunitz.food.api.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;
import me.andrekunitz.food.domain.model.Cuisine;

import java.util.List;

@JacksonXmlRootElement(localName = "cuisines")
@Data
public class CuisineXmlWrapper {

	@JacksonXmlProperty(localName = "cuisine")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private List<Cuisine> cuisines;
}
