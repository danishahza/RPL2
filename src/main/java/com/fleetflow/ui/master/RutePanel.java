package com.fleetflow.ui.master;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.Rute;

/**
 * Halaman CRUD data rute untuk role admin.
 */
public class RutePanel extends CrudPanel<Rute> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "ID", "Kota A", "Kota B", "Jarak (km)" };

	public RutePanel(MasterDataController data) {
		super(data, "Rute", KOLOM);
	}

	@Override
	protected List<Rute> daftar() {
		return data.rute();
	}

	@Override
	protected String id(Rute rute) {
		return rute.id();
	}

	@Override
	protected String[] baris(Rute rute) {
		return new String[] { rute.id(), data.namaKota(rute.kotaAId()), data.namaKota(rute.kotaBId()),
				String.valueOf(rute.jarakKm()) };
	}

	@Override
	protected void simpan(Rute rute) {
		if (rute.id() == null) {
			data.tambahRute(rute.kotaAId(), rute.kotaBId(), rute.jarakKm());
		}
		else {
			data.ubahRute(rute.id(), rute.kotaAId(), rute.kotaBId(), rute.jarakKm());
		}
	}

	@Override
	protected void hapusData(Rute rute) {
		data.hapusRute(rute.id());
	}

	@Override
	protected Rute mintaForm(Rute awal) {
		if (data.kota().size() < 2) {
			pesanError("Tambahkan minimal dua kota terlebih dahulu sebelum mengisi rute.");
			return null;
		}

		String id = awal == null ? null : awal.id();
		String[] namaKota = namaKotaTersedia();

		JComboBox<String> kotaA = new JComboBox<>(namaKota);
		JComboBox<String> kotaB = new JComboBox<>(namaKota);
		if (awal != null) {
			kotaA.setSelectedItem(data.namaKota(awal.kotaAId()));
			kotaB.setSelectedItem(data.namaKota(awal.kotaBId()));
		}
		else {
			kotaB.setSelectedIndex(1);
		}

		JTextField jarak = new JTextField(awal == null ? "" : String.valueOf(awal.jarakKm()), 6);

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Kota A"));
		form.add(kotaA);
		form.add(new JLabel("Kota B"));
		form.add(kotaB);
		form.add(new JLabel("Jarak (km)"));
		form.add(jarak);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, form, (awal == null ? "Tambah" : "Ubah") + " Rute",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				return new Rute(id, idKotaDipilih(kotaA), idKotaDipilih(kotaB), angkaWajib(jarak, "Jarak"));
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
	}

}
