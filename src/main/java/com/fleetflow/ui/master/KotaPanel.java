package com.fleetflow.ui.master;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.domain.Kota;

/**
 * Halaman CRUD data kota untuk role admin.
 */
public class KotaPanel extends CrudPanel<Kota> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "ID", "Nama Kota" };

	public KotaPanel(MasterData data) {
		super(data, "Kota", KOLOM);
	}

	@Override
	protected List<Kota> daftar() {
		return data.kota();
	}

	@Override
	protected String id(Kota kota) {
		return kota.id();
	}

	@Override
	protected String[] baris(Kota kota) {
		return new String[] { kota.id(), kota.nama() };
	}

	@Override
	protected void simpan(Kota kota) {
		if (kota.id() == null) {
			data.tambahKota(kota.nama());
		}
		else {
			data.ubahKota(kota.id(), kota.nama());
		}
	}

	@Override
	protected void hapusData(Kota kota) {
		data.hapusKota(kota.id());
	}

	@Override
	protected Kota mintaForm(Kota awal) {
		String id = awal == null ? null : awal.id();
		JTextField nama = new JTextField(awal == null ? "" : awal.nama(), 20);

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Nama Kota"));
		form.add(nama);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, form, (awal == null ? "Tambah" : "Ubah") + " Kota",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				return new Kota(id, wajib(nama, "Nama kota"));
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
	}

}
