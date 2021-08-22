package me.andrekunitz.food.domain.service;

import me.andrekunitz.food.domain.filter.DailySaleFilter;

public interface SaleReportService {

	byte[] issueSalesReport(DailySaleFilter filter, String timeOffset);

}
