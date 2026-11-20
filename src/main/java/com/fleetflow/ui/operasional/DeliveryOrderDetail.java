package com.fleetflow.ui.operasional;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.DeliveryOrderItem;

/**
 * Menampilkan rincian lengkap satu delivery order dalam dialog.
 */
final class DeliveryOrderDetail {

	private static final DateTimeFormatter TANGGAL = DateTimeFormatter.ofPattern("dd MMMM yyyy",
			Locale.forLanguageTag("id-ID"));

	private DeliveryOrderDetail() {
	}

	static void tampilkan(Component induk, MasterDataController data, DeliveryOrder deliveryOrder) {
		StringBuilder html = new StringBuilder("<html><body style='width: 420px'>");
		baris(html, "No. DO", deliveryOrder.nomorDo());
		baris(html, "Status", deliveryOrder.status().getLabel());
		baris(html, "Driver", deliveryOrder.driverId() == null ? "Belum ditugaskan"
				: data.namaDriver(deliveryOrder.driverId()));
		baris(html, "Tanggal Pembuatan Delivery Order", tanggal(deliveryOrder.tanggalDo()));
		baris(html, "Tanggal Loading", tanggal(deliveryOrder.tanggalPickup()));
		baris(html, "Tenggat Pengiriman", tanggal(deliveryOrder.tanggalTenggat()));

		html.append("<br>");
		baris(html, "Asal", data.namaKota(deliveryOrder.kotaAsalId()));
		baris(html, "Pengirim", data.namaPelanggan(deliveryOrder.pengirimId()));
		baris(html, "Alamat Pengirim", data.alamatPelanggan(deliveryOrder.pengirimId()));
		baris(html, "Tujuan", data.namaKota(deliveryOrder.kotaTujuanId()));
		baris(html, "Penerima", data.namaPelanggan(deliveryOrder.penerimaId()));
		baris(html, "Alamat Penerima", data.alamatPelanggan(deliveryOrder.penerimaId()));

		html.append("<br><b>Produk:</b><br>");
		for (DeliveryOrderItem item : deliveryOrder.items()) {
			html.append("&bull; ")
					.append(escape(data.namaProduk(item.produkId())))
					.append(" &mdash; ")
					.append(String.format(Locale.US, "%.2f", item.beratTon()))
					.append(" ton<br>");
		}
		html.append("<br><b>Total berat:</b> ")
				.append(String.format(Locale.US, "%.2f", deliveryOrder.totalBeratTon()))
				.append(" ton");
		html.append("</body></html>");

		JLabel isi = new JLabel(html.toString());
		isi.setVerticalAlignment(SwingConstants.TOP);
		JScrollPane gulir = new JScrollPane(isi);
		gulir.setPreferredSize(new Dimension(470, 360));

		Object[] opsi = { "Cetak PDF", "Tutup" };
		int pilihan = JOptionPane.showOptionDialog(induk, gulir, "Detail Delivery Order " + deliveryOrder.nomorDo(),
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opsi, opsi[1]);
		if (pilihan == 0) {
			cetak(induk, data, deliveryOrder);
		}
	}

	/**
	 * Mencetak DO ini ke berkas PDF melalui template JasperReports. Pengguna
	 * memilih lokasi penyimpanan lebih dahulu.
	 */
	private static void cetak(Component induk, MasterDataController data, DeliveryOrder deliveryOrder) {
		JFileChooser pemilih = new JFileChooser();
		pemilih.setDialogTitle("Simpan Invoice Delivery Order sebagai PDF");
		pemilih.setFileFilter(new FileNameExtensionFilter("Berkas PDF (*.pdf)", "pdf"));
		pemilih.setSelectedFile(new File("Invoice-" + deliveryOrder.nomorDo() + ".pdf"));
		if (pemilih.showSaveDialog(induk) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File tujuan = pemilih.getSelectedFile();
		if (!tujuan.getName().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
			tujuan = new File(tujuan.getParentFile(), tujuan.getName() + ".pdf");
		}
		try {
			data.cetakInvoiceDeliveryOrder(deliveryOrder, tujuan);
			JOptionPane.showMessageDialog(induk, "Invoice PDF disimpan di:\n" + tujuan.getAbsolutePath(),
					"Cetak PDF", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (RuntimeException e) {
			JOptionPane.showMessageDialog(induk, "Gagal mencetak delivery order: " + e.getMessage(), "Cetak PDF",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void baris(StringBuilder html, String label, String nilai) {
		html.append("<b>").append(escape(label)).append(":</b> ").append(escape(nilai)).append("<br>");
	}

	private static String tanggal(LocalDate tanggal) {
		return tanggal == null ? "-" : TANGGAL.format(tanggal);
	}

	private static String escape(String nilai) {
		if (nilai == null) {
			return "-";
		}
		return nilai.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

}
