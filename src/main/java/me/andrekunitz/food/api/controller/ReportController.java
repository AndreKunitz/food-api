package me.andrekunitz.food.api.controller;

import static org.springframework.http.MediaType.*;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.filter.DailySaleFilter;
import me.andrekunitz.food.domain.model.dto.DailySale;
import me.andrekunitz.food.domain.service.SaleQueryService;
import me.andrekunitz.food.domain.service.SaleReportService;

@RestController
@RequestMapping(path = "/reports")
@RequiredArgsConstructor
public class ReportController {

	private final SaleQueryService saleQueryService;
	private final SaleReportService saleReportService;

	@GetMapping(path = "/daily-sales", produces = APPLICATION_JSON_VALUE)
	public List<DailySale> searchDailySale(
			DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "-03:00")
			String timeOffset
	) {
		return saleQueryService.searchDailySale(filter, timeOffset);
	}

	@GetMapping(path = "/daily-sales", produces = APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> searchDailySalePdf(
			DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "-03:00")
			String timeOffset
	) {
		var bytesPdf = saleReportService.issueSalesReport(filter, timeOffset);

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; file-name=daily-sales.pdf");

		return ResponseEntity.ok()
				.contentType(APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}

}
