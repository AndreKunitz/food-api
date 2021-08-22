package me.andrekunitz.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.andrekunitz.food.domain.filter.DailySaleFilter;
import me.andrekunitz.food.domain.model.dto.DailySale;
import me.andrekunitz.food.domain.service.SaleQueryService;

@RestController
@RequestMapping(path = "/reports")
public class ReportController {

	@Autowired
	private SaleQueryService saleQueryService;

	@GetMapping("/daily-sales")
	public List<DailySale> searchDailySale(DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "-03:00") String timeOffset
	) {
		return saleQueryService.searchDailySale(filter, timeOffset);
	}
}
