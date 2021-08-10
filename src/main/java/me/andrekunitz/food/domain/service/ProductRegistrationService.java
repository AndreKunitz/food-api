package me.andrekunitz.food.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.exception.ProductNotFoundException;
import me.andrekunitz.food.domain.model.Product;
import me.andrekunitz.food.domain.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductRegistrationService {

	private final ProductRepository productRepository;

	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Product fetchOrFail(Long merchantId, Long productId) {
		return productRepository.findById(merchantId, productId)
				.orElseThrow(() -> new ProductNotFoundException(merchantId, productId));
	}

}
