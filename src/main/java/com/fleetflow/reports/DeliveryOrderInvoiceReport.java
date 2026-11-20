package com.fleetflow.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.fleetflow.domain.DeliveryOrder;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Mencetak invoice delivery order ke berkas PDF memakai template JasperReports
 * {@code DeliveryOrderInvoice.jrxml} pada paket ini.
 *
 * <p>Template dikompilasi sekali, lalu diisi dengan koneksi database agar tabel
 * item DO pada laporan hanya berisi data DO yang sedang dicetak.
 */
@Component
public class DeliveryOrderInvoiceReport {

	private static final String LOKASI_CLASSPATH = "/com/fleetflow/reports/DeliveryOrderInvoice.jrxml";

	private static final String LOKASI_BERKAS = "src/main/java/com/fleetflow/reports/DeliveryOrderInvoice.jrxml";

	private final DataSource dataSource;

	private volatile JasperReport laporan;

	public DeliveryOrderInvoiceReport(DataSource dataSource) {
		this.dataSource = dataSource;
		// Template memakai font DejaVu Sans yang belum tentu terpasang di Windows;
		// tanpa properti ini JasperReports menggagalkan pengisian laporan.
		DefaultJasperReportsContext.getInstance()
				.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
	}

	/**
	 * Mengisi laporan untuk satu DO lalu menyimpannya sebagai berkas PDF.
	 */
	public void simpanPdf(DeliveryOrder deliveryOrder, File tujuan) {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("nomor_do", deliveryOrder.nomorDo());
		try (Connection koneksi = dataSource.getConnection()) {
			JasperPrint hasil = JasperFillManager.fillReport(muatLaporan(), parameter, koneksi);
			JasperExportManager.exportReportToPdfFile(hasil, tujuan.getAbsolutePath());
		}
		catch (SQLException | JRException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private JasperReport muatLaporan() throws JRException {
		JasperReport hasil = laporan;
		if (hasil == null) {
			hasil = JasperCompileManager.compileReport(bukaTemplate());
			laporan = hasil;
		}
		return hasil;
	}

	private InputStream bukaTemplate() throws JRException {
		InputStream aliran = DeliveryOrderInvoiceReport.class.getResourceAsStream(LOKASI_CLASSPATH);
		if (aliran != null) {
			return aliran;
		}
		File berkas = new File(LOKASI_BERKAS);
		if (berkas.isFile()) {
			try {
				return new FileInputStream(berkas);
			}
			catch (IOException e) {
				throw new JRException("Gagal membaca template laporan: " + berkas.getPath(), e);
			}
		}
		throw new JRException("Template laporan tidak ditemukan: " + LOKASI_CLASSPATH);
	}

}
