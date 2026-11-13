package com.fleetflow.ui.master;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.domain.Pelanggan;

/**
 * Halaman CRUD data pelanggan untuk role admin.
 */
public class PelangganPanel extends CrudPanel<Pelanggan> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "ID", "Nama", "Alamat", "Kota" };

	public PelangganPanel(MasterData data) {
		super(data, "Pelanggan", KOLOM);
	}

	@Override
	protected List<Pelanggan> daftar() {
		return data.pelanggan();
	}

	@Override
	protected String id(Pelanggan pelanggan) {
		return pelanggan.id();
	}

	@Override
	protected String[] baris(Pelanggan pelanggan) {
		return new String[] { pelanggan.id(), pelanggan.nama(), pelanggan.alamat(),
				data.namaKota(pelanggan.kotaId()) };
	}

	@Override
	protected void simpan(Pelanggan pelanggan) {
		if (pelanggan.id() == null) {
			data.tambahPelanggan(pelanggan.nama(), pelanggan.alamat(), pelanggan.kotaId());
		}
		else {
			data.ubahPelanggan(pelanggan.id(), pelanggan.nama(), pelanggan.alamat(), pelanggan.kotaId());
		}
	}

	@Override
	protected void hapusData(Pelanggan pelanggan) {
		data.hapusPelanggan(pelanggan.id());
	}

	@Override
	protected Pelanggan mintaForm(Pelanggan awal) {
		if (data.kota().isEmpty()) {
			pesanError("Tambahkan data kota terlebih dahulu sebelum mengisi pelanggan.");
			return null;
		}

		String id = awal == null ? null : awal.id();
		JTextField nama = new JTextField(awal == null ? "" : awal.nama(), 20);
		JTextField alamat = new JTextField(awal == null ? "" : awal.alamat(), 20);

		JComboBox<String> kota = new JComboBox<>(namaKotaTersedia());
		if (awal != null) {
			kota.setSelectedItem(data.namaKota(awal.kotaId()));
		}

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Nama"));
		form.add(nama);
		form.add(new JLabel("Alamat"));
		form.add(alamat);
		form.add(new JLabel("Kota"));
		form.add(kota);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, form,
					(awal == null ? "Tambah" : "Ubah") + " Pelanggan", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				return new Pelanggan(id, wajib(nama, "Nama"), wajib(alamat, "Alamat"), idKotaDipilih(kota));
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
	}

}
