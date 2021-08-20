package me.andrekunitz.food.domain.repository.filter;

import static org.springframework.format.annotation.DateTimeFormat.*;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFilter {

	private Long clientId;
	private Long merchantId;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime registrationDateBegin;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime registrationDateEnd;

}
