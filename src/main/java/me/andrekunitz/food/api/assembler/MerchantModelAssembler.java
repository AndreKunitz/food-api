package me.andrekunitz.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.MerchantModel;
import me.andrekunitz.food.domain.model.Merchant;

@Component
@RequiredArgsConstructor
public class MerchantModelAssembler {

	private final ModelMapper modelMapper;

	public MerchantModel toModel(Merchant merchant) {
		return modelMapper.map(merchant, MerchantModel.class);
	}

	public List<MerchantModel> toCollectionModel(List<Merchant> merchants) {
		return merchants.stream()
				.map(merchant -> toModel(merchant))
				.collect(Collectors.toList());
	}

}
