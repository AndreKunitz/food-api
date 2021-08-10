package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.ProductModel;
import me.andrekunitz.food.domain.model.Product;

@Component
@RequiredArgsConstructor
public class ProductModelAssembler {

	private final ModelMapper modelMapper;

	public ProductModel toModel(Product product) {
		return modelMapper.map(product, ProductModel.class);
	}

	public List<ProductModel> toCollectionModel(List<Product> products) {
		return products.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
