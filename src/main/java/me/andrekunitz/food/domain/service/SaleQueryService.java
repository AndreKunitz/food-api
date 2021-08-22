package me.andrekunitz.food.domain.service;

import java.util.List;

import me.andrekunitz.food.domain.filter.DailySaleFilter;
import me.andrekunitz.food.domain.model.dto.DailySale;

public interface SaleQueryService {

	List<DailySale> searchDailySale(DailySaleFilter filter, String timeOffset);

}
