package me.andrekunitz.food.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.api.assembler.UserModelAssembler;
import me.andrekunitz.food.api.model.UserModel;
import me.andrekunitz.food.domain.service.MerchantRegistrationService;

@RestController
@RequestMapping("/merchants/{merchantId}/responsible")
@RequiredArgsConstructor
public class MerchantResponsibleUserController {

	private final MerchantRegistrationService merchantRegistration;
	private final UserModelAssembler userModelAssembler;

	@GetMapping
	public List<UserModel> list(@PathVariable Long merchantId) {
		var merchant = merchantRegistration.fetchOrFail(merchantId);

		return userModelAssembler.toCollectionModel(merchant.getResponsible());
	}

	@PutMapping("{userId}")
	@ResponseStatus(NO_CONTENT)
	public void associate(@PathVariable Long merchantId,
						  @PathVariable Long userId
	) {
		merchantRegistration.associateResponsible(merchantId, userId);
	}

	@DeleteMapping("{groupId}")
	@ResponseStatus(NO_CONTENT)
	public void disassociate(@PathVariable Long merchantId,
							 @PathVariable Long groupId
	) {
		merchantRegistration.disassociateResponsible(merchantId, groupId);
	}
}
