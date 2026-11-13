package com.fleetflow.ui.master;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.Produk;

/**
 * Halaman CRUD data produk untuk role admin.
 *
 * <p>Berat muatan tidak diisi di sini karena diinput per delivery order oleh
 * role operasional.
 */
public class ProdukPanel extends CrudPanel<Produk> {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "ID", "Nama Produk" };

	public ProdukPanel(MasterDataController data) {
		super(data, "Produk", KOLOM);
	}

	@Override
	protected List<Produk> daftar() {
		return data.produk();
	}

	@Override
	protected String id(Produk produk) {
		return produk.id();
	}

	@Override
	protected String[] baris(Produk produk) {
		return new String[] { produk.id(), produk.nama() };
	}

	@Override
	protected void simpan(Produk produk) {
		if (produk.id() == null) {
			data.tambahProduk(produk.nama());
		}
		else {
			data.ubahProduk(produk.id(), produk.nama());
		}
	}

	@Override
	protected void hapusData(Produk produk) {
		data.hapusProduk(produk.id());
	}

	@Override
	protected Produk mintaForm(Produk awal) {
		String id = awal == null ? null : awal.id();
		JTextField nama = new JTextField(awal == null ? "" : awal.nama(), 20);

		JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
		form.add(new JLabel("Nama Produk"));
		form.add(nama);

		while (true) {
			int pilihan = JOptionPane.showConfirmDialog(this, form, (awal == null ? "Tambah" : "Ubah") + " Produk",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (pilihan != JOptionPane.OK_OPTION) {
				return null;
			}
			try {
				return new Produk(id, wajib(nama, "Nama produk"));
			}
			catch (IllegalArgumentException e) {
				pesanError(e.getMessage());
			}
		}
	}

}
