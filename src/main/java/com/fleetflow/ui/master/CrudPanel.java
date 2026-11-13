package com.fleetflow.ui.master;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fleetflow.domain.Kota;
import com.fleetflow.ui.TabelPanel;

/**
 * Panel CRUD umum: tabel, pencarian, dan tombol Tambah/Ubah/Hapus.
 *
 * @param <T> jenis data master yang dikelola
 */
public abstract class CrudPanel<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	protected final MasterData data;
	private final TabelPanel tabel;

	protected CrudPanel(MasterData data, String judul, String[] kolom) {
		super(new BorderLayout());
		this.data = data;
		this.tabel = new TabelPanel(judul, kolom, this::barisTampilan);

		JButton tambah = new JButton("Tambah");
		tambah.addActionListener(e -> tambah());
		JButton ubah = new JButton("Ubah");
		ubah.addActionListener(e -> ubah());
		JButton hapus = new JButton("Hapus");
		hapus.addActionListener(e -> hapus());

		tabel.tambahTombol(tambah);
		tabel.tambahTombol(ubah);
		tabel.tambahTombol(hapus);

		add(tabel, BorderLayout.CENTER);
	}

	protected abstract List<T> daftar();

	protected abstract String[] baris(T item);

	protected abstract String id(T item);

	/**
	 * Menampilkan form. {@code awal} bernilai {@code null} saat menambah data.
	 * Mengembalikan {@code null} jika pengguna membatalkan form.
	 */
	protected abstract T mintaForm(T awal);

	protected abstract void simpan(T item);

	protected abstract void hapusData(T item);

	private List<String[]> barisTampilan() {
		return daftar().stream().map(this::baris).toList();
	}

	private void tambah() {
		T baru = mintaForm(null);
		if (baru != null) {
			simpanAman(baru);
		}
	}

	private void ubah() {
		T awal = dataTerpilih();
		if (awal == null) {
			return;
		}
		T hasil = mintaForm(awal);
		if (hasil != null) {
			simpanAman(hasil);
		}
	}

	private void hapus() {
		T item = dataTerpilih();
		if (item == null) {
			return;
		}
		int pilihan = JOptionPane.showConfirmDialog(this, "Hapus data dengan ID " + id(item) + "?",
				"Konfirmasi", JOptionPane.YES_NO_OPTION);
		if (pilihan != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			hapusData(item);
			tabel.refresh();
		}
		catch (IllegalArgumentException e) {
			pesanError(e.getMessage());
		}
	}

	private T dataTerpilih() {
		String id = tabel.idTerpilih();
		if (id == null) {
			JOptionPane.showMessageDialog(this, "Pilih baris pada tabel terlebih dahulu.", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		return daftar().stream().filter(item -> id(item).equals(id)).findFirst().orElse(null);
	}

	private void simpanAman(T item) {
		try {
			simpan(item);
			tabel.refresh();
		}
		catch (IllegalArgumentException e) {
			pesanError(e.getMessage());
		}
	}

	protected void pesanError(String pesan) {
		JOptionPane.showMessageDialog(this, pesan, "Gagal", JOptionPane.ERROR_MESSAGE);
	}

	protected static String wajib(JTextField field, String label) {
		String nilai = field.getText().trim();
		if (nilai.isEmpty()) {
			throw new IllegalArgumentException(label + " wajib diisi.");
		}
		return nilai;
	}

	protected static int angkaWajib(JTextField field, String label) {
		try {
			return Integer.parseInt(wajib(field, label));
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException(label + " harus berupa angka bulat.");
		}
	}

	protected String[] namaKotaTersedia() {
		return data.kota().stream().map(Kota::nama).toArray(String[]::new);
	}

	protected String idKotaDipilih(JComboBox<String> combo) {
		Object nama = combo.getSelectedItem();
		return data.kota().stream()
				.filter(kota -> kota.nama().equals(nama))
				.map(Kota::id)
				.findFirst()
				.orElse(null);
	}

}
