package me.andrekunitz.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.model.input.MerchantInput;
import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.model.Merchant;

@Component
@RequiredArgsConstructor
public class MerchantInputDisassembler {

	private final ModelMapper modelMapper;

	public Merchant toDomainObject(MerchantInput merchantInput) {
		return modelMapper.map(merchantInput, Merchant.class);
	}

	public void copyToDomainObject(MerchantInput merchantInput, Merchant merchant) {
		/*
			To avoid org.hibernate.HibernateException: identifier of an instance of
			me.andrekunitz.food.domain.model.Cuisine was altered from 1 to 2
		*/
		merchant.setCuisine(new Cuisine());

		modelMapper.map(merchantInput, merchant);
	}

	public MerchantInput toInputAssembler(Merchant merchant) {
		return modelMapper.map(merchant, MerchantInput.class);
	}

}
