package me.andrekunitz.food.infrastructure.repository.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.andrekunitz.food.domain.filter.DailySaleFilter;
import me.andrekunitz.food.domain.service.SaleQueryService;
import me.andrekunitz.food.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
public class PdfReportServiceImpl implements SaleReportService {

	private final SaleQueryService saleQueryService;

	@Override
	public byte[] issueSalesReport(DailySaleFilter filter, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream(
					"/reports/daily-sales.jasper");

			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var dailySales = saleQueryService.searchDailySale(filter, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(dailySales);

			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("System could not issue daily sales report", e);
		}
	}
}
