package me.andrekunitz.food.domain.filter;

import static org.springframework.format.annotation.DateTimeFormat.*;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailySaleFilter {

	private Long merchantId;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dateTimeBeginning;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dateTimeEnding;
}
