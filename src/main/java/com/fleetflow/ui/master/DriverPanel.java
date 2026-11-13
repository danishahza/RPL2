package com.fleetflow.ui.master;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.Driver;
import com.fleetflow.domain.StatusDriver;

/**
 * Halaman CRUD data driver untuk role admin.
 */
public class DriverPanel extends CrudPanel<Driver> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "ID", "Nama", "No. Telepon", "No. Fleet", "Kapasitas", "Status",
			"Kota Saat Ini" };

	public DriverPanel(MasterDataController data) {
		super(data, "Driver", KOLOM);
	}

	@Override
	protected List<Driver> daftar() {
		return data.driver();
	}

	@Override
	protected String id(Driver driver) {
		return driver.id();
	}

	@Override
	protected String[] baris(Driver driver) {
		return new String[] { driver.id(), driver.nama(), driver.noTelepon(), driver.nomorFleet(),
				driver.kapasitasTon() + " ton", driver.status().getLabel(), data.namaKota(driver.kotaSaatIniId()) };
	}

	@Override
	protected void simpan(Driver driver) {
		if (driver.id() == null) {
			data.tambahDriver(driver.nama(), driver.noTelepon(), driver.nomorFleet(), driver.kapasitasTon(),
					driver.status(), driver.kotaSaatIniId());
		}
		else {
			data.ubahDriver(driver.id(), driver.nama(), driver.noTelepon(), driver.nomorFleet(),
					driver.kapasitasTon(), driver.status(), driver.kotaSaatIniId());
		}
	}

	@Override
	protected void hapusData(Driver driver) {
		data.hapusDriver(driver.id());
	}

	@Override
	protected Driver mintaForm(Driver awal) {
		if (data.kota().isEmpty()) {
			pesanError("Tambahkan data kota terlebih dahulu sebelum mengisi driver.");
			return null;
		}

		String id = awal == null ? null : awal.id();
		JTextField nama = new JTextField(awal == null ? "" : awal.nama(), 20);
		JTextField telepon = new JTextField(awal == null ? "" : awal.noTelepon(), 20);
		JTextField fleet = new JTextField(awal == null ? "" : awal.nomorFleet(), 10);
		JTextField kapasitas = new JTextField(awal == null ? "60" : String.valueOf(awal.kapasitasTon()), 6);

		JComboBox<StatusDriver> status = new JComboBox<>(StatusDriver.values());
		if (awal != null) {
			status.setSelectedItem(awal.status());
		}

		JComboBox<String> kota = new JComboBox<>(namaKotaTersedia());
		if (awal != null) {
			kota.setSelectedItem(data.namaKota(awal.kotaSaatIniId()));
		}

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Nama"));
		form.add(nama);
		form.add(new JLabel("No. HP"));
		form.add(telepon);
		form.add(new JLabel("No. Fleet"));
		form.add(fleet);
		form.add(new JLabel("Kapasitas (ton)"));
		form.add(kapasitas);
		form.add(new JLabel("Status"));
		form.add(status);
		form.add(new JLabel("Kota Saat Ini (posisi truk)"));
		form.add(kota);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, form, (awal == null ? "Tambah" : "Ubah") + " Driver",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				return new Driver(id, wajib(nama, "Nama"), wajib(telepon, "No. HP"), wajib(fleet, "No. Fleet"),
						angkaWajib(kapasitas, "Kapasitas"), (StatusDriver) status.getSelectedItem(),
						idKotaDipilih(kota));
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
	}

}
