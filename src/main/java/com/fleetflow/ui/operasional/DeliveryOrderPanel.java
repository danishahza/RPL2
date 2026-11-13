package com.fleetflow.ui.operasional;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.ui.master.CrudPanel;

/**
 * Halaman delivery order untuk role operasional: daftar DO dan form pembuatan
 * DO berisi rute, pengirim, penerima, tanggal, produk, dan berat muatan.
 */
public class DeliveryOrderPanel extends CrudPanel<DeliveryOrder> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "No. DO", "Kota Asal", "Pengirim", "Kota Tujuan", "Penerima", "Produk",
			"Berat (ton)", "Tgl DO", "Pickup", "Tenggat" };

	public DeliveryOrderPanel(MasterDataController data) {
		super(data, "Delivery Order", KOLOM);
	}

	@Override
	protected List<DeliveryOrder> daftar() {
		return data.deliveryOrder();
	}

	@Override
	protected String id(DeliveryOrder deliveryOrder) {
		return deliveryOrder.nomorDo();
	}

	@Override
	protected String[] baris(DeliveryOrder deliveryOrder) {
		return new String[] { deliveryOrder.nomorDo(), data.namaKota(deliveryOrder.kotaAsalId()),
				data.namaPelanggan(deliveryOrder.pengirimId()), data.namaKota(deliveryOrder.kotaTujuanId()),
				data.namaPelanggan(deliveryOrder.penerimaId()), data.namaProduk(deliveryOrder.produkId()),
				String.format(Locale.US, "%.2f", deliveryOrder.beratTon()), deliveryOrder.tanggalDo().toString(),
				deliveryOrder.tanggalPickup().toString(), deliveryOrder.tanggalTenggat().toString() };
	}

	@Override
	protected void simpan(DeliveryOrder deliveryOrder) {
		if (deliveryOrder.id() == null) {
			data.tambahDeliveryOrder(deliveryOrder.kotaAsalId(), deliveryOrder.pengirimId(),
					deliveryOrder.kotaTujuanId(), deliveryOrder.penerimaId(), deliveryOrder.tanggalDo(),
					deliveryOrder.tanggalPickup(), deliveryOrder.tanggalTenggat(), deliveryOrder.produkId(),
					deliveryOrder.beratTon());
		}
		else {
			data.ubahDeliveryOrder(deliveryOrder.id(), deliveryOrder.kotaAsalId(), deliveryOrder.pengirimId(),
					deliveryOrder.kotaTujuanId(), deliveryOrder.penerimaId(), deliveryOrder.tanggalDo(),
					deliveryOrder.tanggalPickup(), deliveryOrder.tanggalTenggat(), deliveryOrder.produkId(),
					deliveryOrder.beratTon());
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
		JComboBox<Pilihan> produk = new JComboBox<>(
				daftarProduk.stream().map(item -> new Pilihan(item.id(), item.nama())).toArray(Pilihan[]::new));

		pilih(kotaAsal, awal == null ? null : awal.kotaAsalId(), 0);
		isiPelanggan(pengirim, daftarPelanggan, idTerpilih(kotaAsal), awal == null ? null : awal.pengirimId());
		pilih(kotaTujuan, awal == null ? null : awal.kotaTujuanId(), 1);
		isiPelanggan(penerima, daftarPelanggan, idTerpilih(kotaTujuan), awal == null ? null : awal.penerimaId());
		pilih(produk, awal == null ? null : awal.produkId(), 0);

		kotaAsal.addActionListener(e -> isiPelanggan(pengirim, daftarPelanggan, idTerpilih(kotaAsal), null));
		kotaTujuan.addActionListener(e -> isiPelanggan(penerima, daftarPelanggan, idTerpilih(kotaTujuan), null));

		JTextField tanggalDo = new JTextField(
				awal == null ? LocalDate.now().toString() : awal.tanggalDo().toString(), 12);
		JTextField tanggalPickup = new JTextField(awal == null ? "" : awal.tanggalPickup().toString(), 12);
		JTextField tanggalTenggat = new JTextField(awal == null ? "" : awal.tanggalTenggat().toString(), 12);
		JTextField berat = new JTextField(awal == null ? "" : String.format(Locale.US, "%.2f", awal.beratTon()), 8);

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Kota asal"));
		form.add(kotaAsal);
		form.add(new JLabel("Pengirim (perusahaan asal)"));
		form.add(pengirim);
		form.add(new JLabel("Kota tujuan"));
		form.add(kotaTujuan);
		form.add(new JLabel("Penerima (perusahaan tujuan)"));
		form.add(penerima);
		form.add(new JLabel("Tanggal pembuatan DO (yyyy-MM-dd)"));
		form.add(tanggalDo);
		form.add(new JLabel("Jadwal pickup (yyyy-MM-dd)"));
		form.add(tanggalPickup);
		form.add(new JLabel("Tenggat pengiriman (yyyy-MM-dd)"));
		form.add(tanggalTenggat);
		form.add(new JLabel("Produk (barang muatan)"));
		form.add(produk);
		form.add(new JLabel("Berat muatan (ton)"));
		form.add(berat);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, form,
					(awal == null ? "Tambah" : "Ubah") + " Delivery Order", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				LocalDate tglDo = tanggalWajib(tanggalDo, "Tanggal pembuatan DO");
				LocalDate tglPickup = tanggalWajib(tanggalPickup, "Jadwal pickup");
				LocalDate tglTenggat = tanggalWajib(tanggalTenggat, "Tenggat pengiriman");
				if (tglPickup.isBefore(tglDo)) {
					throw new IllegalArgumentException("Jadwal pickup tidak boleh sebelum tanggal pembuatan DO.");
				}
				if (tglTenggat.isBefore(tglPickup)) {
					throw new IllegalArgumentException(
							"Tenggat waktu pengiriman tidak boleh sebelum jadwal pickup.");
				}
				return new DeliveryOrder(awal == null ? null : awal.id(),
						awal == null ? null : awal.nomorDo(), idTerpilih(kotaAsal), idWajib(pengirim, "Pengirim"),
						idTerpilih(kotaTujuan), idWajib(penerima, "Penerima"), tglDo, tglPickup, tglTenggat,
						idWajib(produk, "Produk"), beratWajib(berat));
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
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

	private static LocalDate tanggalWajib(JTextField field, String label) {
		String nilai = wajib(field, label);
		try {
			return LocalDate.parse(nilai);
		}
		catch (DateTimeParseException e) {
			throw new IllegalArgumentException(label + " harus berformat yyyy-MM-dd, contoh 2026-11-13.");
		}
	}

	private static double beratWajib(JTextField field) {
		String nilai = wajib(field, "Berat muatan").replace(',', '.');
		double berat;
		try {
			berat = Double.parseDouble(nilai);
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Berat muatan harus berupa angka, contoh 12.5 ton.");
		}
		if (!Double.isFinite(berat) || berat <= 0) {
			throw new IllegalArgumentException("Berat muatan harus lebih dari 0 ton.");
		}
		return berat;
	}

	/** Pilihan combo yang menampilkan nama tetapi menyimpan ID. */
	private record Pilihan(String id, String nama) {

		@Override
		public String toString() {
			return nama;
		}

	}

}
