package me.andrekunitz.food.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DailySale {

	private Date date;
	private Long totalSales;
	private BigDecimal totalBilled;

}
