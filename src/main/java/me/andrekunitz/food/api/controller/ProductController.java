package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.ProductInputDisassembler;
import me.andrekunitz.food.api.assembler.ProductModelAssembler;
import me.andrekunitz.food.api.model.ProductModel;
import me.andrekunitz.food.api.model.input.ProductInput;
import me.andrekunitz.food.domain.repository.ProductRepository;
import me.andrekunitz.food.domain.service.MerchantRegistrationService;
import me.andrekunitz.food.domain.service.ProductRegistrationService;

@RestController
@RequestMapping("merchants/{merchantId}/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductRepository productRepository;
	private final ProductRegistrationService productRegistration;
	private final MerchantRegistrationService merchantRegistration;
	private final ProductModelAssembler productModelAssembler;
	private final ProductInputDisassembler productInputDisassembler;

	@GetMapping
	public List<ProductModel> list(@PathVariable Long merchantId) {
		var merchant = merchantRegistration.fetchOrFail(merchantId);

		return productModelAssembler.toCollectionModel(
				productRepository.findByMerchant(merchant));
	}

	@GetMapping("{productId}")
	public ProductModel search(@PathVariable Long merchantId,
							   @PathVariable Long productId
	) {
		return productModelAssembler.toModel(
				productRegistration.fetchOrFail(merchantId, productId));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public ProductModel add(@PathVariable Long merchantId,
							@RequestBody @Valid ProductInput productInput
	) {
		var merchant = merchantRegistration.fetchOrFail(merchantId);

		var product = productInputDisassembler.toDomainObject(productInput);
		product.setMerchant(merchant);

		return productModelAssembler.toModel(
				productRegistration.save(product));
	}

	@PutMapping("/{productId}")
	public ProductModel update(@PathVariable Long merchantId,
							   @PathVariable Long productId,
							   @RequestBody @Valid ProductInput productInput
	) {
		var currentProduct = productRegistration.fetchOrFail(merchantId, productId);
		productInputDisassembler.copyToDomainObject(productInput, currentProduct);

		return productModelAssembler.toModel(
				productRegistration.save(currentProduct));
	}

}
