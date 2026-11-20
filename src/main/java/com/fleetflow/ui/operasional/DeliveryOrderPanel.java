package com.fleetflow.ui.operasional;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.DeliveryOrderItem;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.StatusDeliveryOrder;
import com.fleetflow.ui.master.CrudPanel;

/**
 * Halaman delivery order untuk role operasional: daftar DO aktif dan form
 * pembuatan DO berisi rute, pengirim, penerima, tanggal, serta banyak produk.
 *
 * <p>Klik dua kali pada baris (atau tombol Detail) menampilkan rincian lengkap
 * DO, termasuk alamat pengirim, alamat penerima, dan daftar produk beserta berat.
 */
public class DeliveryOrderPanel extends CrudPanel<DeliveryOrder> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "No DO", "Asal", "Pengirim", "Tujuan", "Penerima", "Tgl DO", "Loading",
			"Deadline", "Status" };

	private static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("dd MMM yyyy",
			Locale.forLanguageTag("id-ID"));

	public DeliveryOrderPanel(MasterDataController data) {
		super(data, "Delivery Order", KOLOM);

		JButton detail = new JButton("Detail");
		detail.addActionListener(e -> tampilkanDetail());
		JButton selesaikan = new JButton("Selesaikan DO");
		selesaikan.addActionListener(e -> selesaikanDeliveryOrder());
		tabelPanel().tambahTombol(detail);
		tabelPanel().tambahTombol(selesaikan);
		tabelPanel().saatBarisDiklik(this::tampilkanDetail);
	}

	@Override
	protected List<DeliveryOrder> daftar() {
		return data.deliveryOrderAktif();
	}

	@Override
	protected String id(DeliveryOrder deliveryOrder) {
		return deliveryOrder.nomorDo();
	}

	@Override
	protected String[] baris(DeliveryOrder deliveryOrder) {
		return new String[] { deliveryOrder.nomorDo(), data.namaKota(deliveryOrder.kotaAsalId()),
				data.namaPelanggan(deliveryOrder.pengirimId()), data.namaKota(deliveryOrder.kotaTujuanId()),
				data.namaPelanggan(deliveryOrder.penerimaId()), deliveryOrder.tanggalDo().toString(),
				deliveryOrder.tanggalPickup().toString(), deliveryOrder.tanggalTenggat().toString(),
				deliveryOrder.status().getLabel() };
	}

	@Override
	protected void simpan(DeliveryOrder deliveryOrder) {
		if (deliveryOrder.id() == null) {
			data.tambahDeliveryOrder(deliveryOrder);
		}
		else {
			data.ubahDeliveryOrder(deliveryOrder);
		}
	}

	@Override
	protected void hapusData(DeliveryOrder deliveryOrder) {
		data.hapusDeliveryOrder(deliveryOrder.id());
	}

	@Override
	protected DeliveryOrder mintaForm(DeliveryOrder awal) {
		List<Kota> daftarKota = data.kota();
		List<Pelanggan> daftarPelanggan = data.pelanggan();
		List<Produk> daftarProduk = data.produk();
		if (daftarKota.size() < 2) {
			pesanError("Tambahkan minimal dua kota terlebih dahulu sebelum membuat delivery order.");
			return null;
		}
		if (daftarProduk.isEmpty()) {
			pesanError("Tambahkan minimal satu produk terlebih dahulu sebelum membuat delivery order.");
			return null;
		}

		JComboBox<Pilihan> kotaAsal = new JComboBox<>(pilihanKota(daftarKota));
		JComboBox<Pilihan> pengirim = new JComboBox<>();
		JComboBox<Pilihan> kotaTujuan = new JComboBox<>(pilihanKota(daftarKota));
		JComboBox<Pilihan> penerima = new JComboBox<>();

		pilih(kotaAsal, awal == null ? null : awal.kotaAsalId(), 0);
		isiPelanggan(pengirim, daftarPelanggan, idTerpilih(kotaAsal), awal == null ? null : awal.pengirimId());
		pilih(kotaTujuan, awal == null ? null : awal.kotaTujuanId(), 1);
		isiPelanggan(penerima, daftarPelanggan, idTerpilih(kotaTujuan), awal == null ? null : awal.penerimaId());

		kotaAsal.addActionListener(e -> isiPelanggan(pengirim, daftarPelanggan, idTerpilih(kotaAsal), null));
		kotaTujuan.addActionListener(e -> isiPelanggan(penerima, daftarPelanggan, idTerpilih(kotaTujuan), null));

		LocalDate tanggalDo = awal == null ? LocalDate.now() : awal.tanggalDo();
		TanggalPicker tanggalLoading = new TanggalPicker(
				awal == null ? LocalDate.now() : awal.tanggalPickup());
		TanggalPicker tanggalDeadline = new TanggalPicker(
				awal == null ? LocalDate.now().plusDays(3) : awal.tanggalTenggat());

		JTable tabelProduk = buatTabelProduk(daftarProduk, awal);
		JPanel panelProduk = new JPanel(new BorderLayout(0, 4));
		panelProduk.setBorder(BorderFactory.createTitledBorder("Produk"));
		panelProduk.add(new JScrollPane(tabelProduk), BorderLayout.CENTER);
		JButton tambahProduk = new JButton("+ Produk");
		JButton hapusProduk = new JButton("- Produk");
		JPanel aksiProduk = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
		aksiProduk.add(tambahProduk);
		aksiProduk.add(hapusProduk);
		panelProduk.add(aksiProduk, BorderLayout.SOUTH);

		DefaultTableModel modelProduk = (DefaultTableModel) tabelProduk.getModel();
		tambahProduk.addActionListener(e -> {
			hentikanEdit(tabelProduk);
			modelProduk.addRow(new Object[] { null, "" });
		});
		hapusProduk.addActionListener(e -> {
			hentikanEdit(tabelProduk);
			int baris = tabelProduk.getSelectedRow();
			if (baris >= 0) {
				modelProduk.removeRow(baris);
			}
			else if (modelProduk.getRowCount() > 0) {
				modelProduk.removeRow(modelProduk.getRowCount() - 1);
			}
		});

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Kota asal"));
		form.add(kotaAsal);
		form.add(new JLabel("Pengirim (perusahaan asal)"));
		form.add(pengirim);
		form.add(new JLabel("Kota tujuan"));
		form.add(kotaTujuan);
		form.add(new JLabel("Penerima (perusahaan tujuan)"));
		form.add(penerima);
		form.add(new JLabel("Tanggal pembuatan DO (otomatis)"));
		form.add(new JLabel(FORMAT_TANGGAL.format(tanggalDo)));
		form.add(new JLabel("Tanggal loading"));
		form.add(tanggalLoading);
		form.add(new JLabel("Tenggat pengiriman"));
		form.add(tanggalDeadline);

		JPanel isi = new JPanel(new BorderLayout(0, 12));
		isi.add(form, BorderLayout.NORTH);
		isi.add(panelProduk, BorderLayout.CENTER);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, isi,
					(awal == null ? "Tambah" : "Ubah") + " Delivery Order", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				hentikanEdit(tabelProduk);
				LocalDate tglLoading = tanggalLoading.nilai();
				LocalDate tglDeadline = tanggalDeadline.nilai();
				if (tglLoading.isBefore(tanggalDo)) {
					throw new IllegalArgumentException("Tanggal loading tidak boleh sebelum tanggal DO.");
				}
				if (tglDeadline.isBefore(tglLoading)) {
					throw new IllegalArgumentException("Tenggat pengiriman tidak boleh sebelum tanggal loading.");
				}

				DeliveryOrder hasil = new DeliveryOrder(awal == null ? null : awal.id(),
						awal == null ? null : awal.nomorDo(), idTerpilih(kotaAsal), idWajib(pengirim, "Pengirim"),
						idTerpilih(kotaTujuan), idWajib(penerima, "Penerima"), tanggalDo, tglLoading, tglDeadline,
						awal == null ? StatusDeliveryOrder.CREATED : awal.status(),
						awal == null ? null : awal.driverId());
				hasil.tetapkanItems(bacaItems(modelProduk, daftarProduk));
				return hasil;
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
	}

	/**
	 * Menutup DO yang sudah diantar dispatcher. Selama belum diselesaikan di
	 * sini, DO tetap tampil pada daftar DO aktif.
	 */
	private void selesaikanDeliveryOrder() {
		String nomor = tabelPanel().idTerpilih();
		if (nomor == null) {
			JOptionPane.showMessageDialog(this, "Pilih baris delivery order terlebih dahulu.", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DeliveryOrder deliveryOrder = daftar().stream()
				.filter(doAktif -> doAktif.nomorDo().equals(nomor))
				.findFirst()
				.orElse(null);
		if (deliveryOrder == null) {
			return;
		}
		if (deliveryOrder.status() != StatusDeliveryOrder.DELIVERED) {
			JOptionPane.showMessageDialog(this,
					"DO hanya dapat diselesaikan setelah dispatcher menyelesaikan pengiriman.", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int pilihan = JOptionPane.showConfirmDialog(this,
				"Selesaikan DO " + deliveryOrder.nomorDo() + "? DO akan hilang dari daftar aktif.", "Konfirmasi",
				JOptionPane.YES_NO_OPTION);
		if (pilihan != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			data.selesaikanDeliveryOrder(deliveryOrder.id());
			tabelPanel().refresh();
		}
		catch (IllegalArgumentException e) {
			pesanError(e.getMessage());
		}
	}

	private void tampilkanDetail() {
		String nomor = tabelPanel().idTerpilih();
		if (nomor == null) {
			JOptionPane.showMessageDialog(this, "Pilih baris delivery order terlebih dahulu.", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		daftar().stream()
				.filter(deliveryOrder -> deliveryOrder.nomorDo().equals(nomor))
				.findFirst()
				.ifPresent(deliveryOrder -> DeliveryOrderDetail.tampilkan(this, data, deliveryOrder));
	}

	private static JTable buatTabelProduk(List<Produk> daftarProduk, DeliveryOrder awal) {
		DefaultTableModel model = new DefaultTableModel(new Object[] { "Produk", "Berat (ton)" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int baris, int kolom) {
				return true;
			}
		};

		if (awal != null && !awal.items().isEmpty()) {
			for (DeliveryOrderItem item : awal.items()) {
				model.addRow(new Object[] { namaProduk(daftarProduk, item.produkId()),
						String.format(Locale.US, "%.2f", item.beratTon()) });
			}
		}
		else {
			model.addRow(new Object[] { null, "" });
		}

		JTable tabel = new JTable(model);
		tabel.setRowHeight(22);
		tabel.setPreferredScrollableViewportSize(new Dimension(360, 110));
		JComboBox<String> comboProduk = new JComboBox<>(
				daftarProduk.stream().map(Produk::nama).toArray(String[]::new));
		tabel.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboProduk));
		return tabel;
	}

	private static List<DeliveryOrderItem> bacaItems(DefaultTableModel model, List<Produk> daftarProduk) {
		List<DeliveryOrderItem> items = new ArrayList<>();
		Set<String> produkDipakai = new HashSet<>();
		for (int baris = 0; baris < model.getRowCount(); baris++) {
			Object nilaiProduk = model.getValueAt(baris, 0);
			Object nilaiBerat = model.getValueAt(baris, 1);
			String nama = nilaiProduk == null ? "" : nilaiProduk.toString().trim();
			String beratTeks = nilaiBerat == null ? "" : nilaiBerat.toString().trim();
			if (nama.isEmpty() && beratTeks.isEmpty()) {
				continue;
			}
			if (nama.isEmpty()) {
				throw new IllegalArgumentException("Produk pada baris " + (baris + 1) + " wajib dipilih.");
			}
			String produkId = idProduk(daftarProduk, nama);
			if (produkId == null) {
				throw new IllegalArgumentException("Produk " + nama + " tidak ditemukan.");
			}
			if (!produkDipakai.add(produkId)) {
				throw new IllegalArgumentException("Produk " + nama + " dipilih lebih dari sekali.");
			}
			items.add(new DeliveryOrderItem(null, null, produkId, beratWajib(beratTeks, nama)));
		}
		if (items.isEmpty()) {
			throw new IllegalArgumentException("Minimal satu produk harus diisi.");
		}
		return items;
	}

	private static double beratWajib(String nilai, String namaProduk) {
		if (nilai.isEmpty()) {
			throw new IllegalArgumentException("Berat produk " + namaProduk + " wajib diisi.");
		}
		double berat;
		try {
			berat = Double.parseDouble(nilai.replace(',', '.'));
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Berat produk " + namaProduk + " harus berupa angka, contoh 12.5 ton.");
		}
		if (!Double.isFinite(berat) || berat <= 0) {
			throw new IllegalArgumentException("Berat produk " + namaProduk + " harus lebih dari 0 ton.");
		}
		return berat;
	}

	private static void hentikanEdit(JTable tabel) {
		if (tabel.isEditing()) {
			tabel.getCellEditor().stopCellEditing();
		}
	}

	private static String namaProduk(List<Produk> daftarProduk, String id) {
		return daftarProduk.stream()
				.filter(produk -> produk.id().equals(id))
				.map(Produk::nama)
				.findFirst()
				.orElse("");
	}

	private static String idProduk(List<Produk> daftarProduk, String nama) {
		return daftarProduk.stream()
				.filter(produk -> produk.nama().equals(nama))
				.map(Produk::id)
				.findFirst()
				.orElse(null);
	}

	private static Pilihan[] pilihanKota(List<Kota> daftarKota) {
		return daftarKota.stream().map(kota -> new Pilihan(kota.id(), kota.nama())).toArray(Pilihan[]::new);
	}

	private static void isiPelanggan(JComboBox<Pilihan> combo, List<Pelanggan> daftarPelanggan, String kotaId,
			String idDipilih) {
		combo.removeAllItems();
		for (Pelanggan pelanggan : daftarPelanggan) {
			if (pelanggan.kotaId().equals(kotaId)) {
				combo.addItem(new Pilihan(pelanggan.id(), pelanggan.nama()));
			}
		}
		if (idDipilih != null) {
			pilih(combo, idDipilih, 0);
		}
	}

	private static void pilih(JComboBox<Pilihan> combo, String id, int cadangan) {
		if (id != null) {
			for (int i = 0; i < combo.getItemCount(); i++) {
				if (combo.getItemAt(i).id().equals(id)) {
					combo.setSelectedIndex(i);
					return;
				}
			}
		}
		if (combo.getItemCount() > cadangan) {
			combo.setSelectedIndex(cadangan);
		}
	}

	private static String idTerpilih(JComboBox<Pilihan> combo) {
		Pilihan pilihan = (Pilihan) combo.getSelectedItem();
		return pilihan == null ? null : pilihan.id();
	}

	private static String idWajib(JComboBox<Pilihan> combo, String label) {
		String id = idTerpilih(combo);
		if (id == null) {
			throw new IllegalArgumentException(label + " wajib dipilih. Pastikan data master terkait sudah tersedia.");
		}
		return id;
	}

	/** Pilihan combo yang menampilkan nama tetapi menyimpan ID. */
	private record Pilihan(String id, String nama) {

		@Override
		public String toString() {
			return nama;
		}

	}

}
