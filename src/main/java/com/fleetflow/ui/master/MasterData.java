package com.fleetflow.ui.master;

import java.util.List;

import com.fleetflow.domain.Driver;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.Rute;

/**
 * Data contoh untuk setiap tabel master.
 */
public final class MasterData {

	private static final List<Driver> DRIVER = List.of(
			new Driver("D001", "Bambang", "NIB23456", "Penjemputan"),
			new Driver("D002", "Andi", "NIB12345", "Penjemputan"),
			new Driver("D003", "Joko", "NIB34567", "Pengantaran Panjang"),
			new Driver("D004", "Rudi", "NIB45678", "Penjemputan"),
			new Driver("D005", "Toni", "NIB56789", "Pengantaran Panjang"));

	private static final List<Kota> KOTA = List.of(
			new Kota("K001", "Jakarta", "10110"),
			new Kota("K002", "Bandung", "40116"),
			new Kota("K003", "Surabaya", "60214"),
			new Kota("K004", "Medan", "20111"),
			new Kota("K005", "Makassar", "90212"));

	private static final List<Pelanggan> PELANGGAN = List.of(
			new Pelanggan("P001", "PT. Jasa Sari", "Jl. Merdeka No. 45", "021-1234567"),
			new Pelanggan("P002", "CV. Angkasa", "Jl. Budi Utomo No. 89", "021-7654321"),
			new Pelanggan("P003", "UD. Sari Daya", "Jl. Veteran No. 15", "031-8521470"),
			new Pelanggan("P004", "CV. Logistik Jaya", "Jl. Gatot Subroto No. 200", "021-9876543"),
			new Pelanggan("P005", "PT. Transport Murah", "Jl. Pemuda No. 7", "031-4567890"));

	private static final List<Produk> PRODUK = List.of(
			new Produk("PR001", "Buku Tulis", "Alat Tulis", "1 kg"),
			new Produk("PR002", "Pensil 2B", "Alat Tulis", "200 gr"),
			new Produk("PR003", "Pulpen", "Alat Tulis", "50 gr"),
			new Produk("PR004", "Smartphone", "Elektronik", "250 gr"),
			new Produk("PR005", "Laptop", "Elektronik", "2 kg"));

	private static final List<Rute> RUTE = List.of(
			new Rute("R001", "Jakarta", "Bandung", "150 km"),
			new Rute("R002", "Jakarta", "Surabaya", "600 km"),
			new Rute("R003", "Bandung", "Surabaya", "650 km"),
			new Rute("R004", "Medan", "Makassar", "1800 km"),
			new Rute("R005", "Jakarta", "Makassar", "2000 km"));

	public static final String[] KOLOM_DRIVER = { "ID", "Nama", "No. PLTN", "Keahlian" };
	public static final String[] KOLOM_KOTA = { "ID", "Nama", "Kode Pos" };
	public static final String[] KOLOM_PELANGGAN = { "ID", "Nama", "Alamat", "Telepon" };
	public static final String[] KOLOM_PRODUK = { "ID", "Nama", "Kategori", "Berat" };
	public static final String[] KOLOM_RUTE = { "ID", "Asal", "Tujuan", "Jarak" };

	public static final List<String[]> BARIS_DRIVER = DRIVER.stream()
			.map(driver -> new String[] { driver.id(), driver.nama(), driver.noPLTN(), driver.keahlian() })
			.toList();

	public static final List<String[]> BARIS_KOTA = KOTA.stream()
			.map(kota -> new String[] { kota.id(), kota.nama(), kota.kodePos() })
			.toList();

	public static final List<String[]> BARIS_PELANGGAN = PELANGGAN.stream()
			.map(pelanggan -> new String[] { pelanggan.id(), pelanggan.nama(), pelanggan.alamat(), pelanggan.telepon() })
			.toList();

	public static final List<String[]> BARIS_PRODUK = PRODUK.stream()
			.map(produk -> new String[] { produk.id(), produk.nama(), produk.kategori(), produk.berat() })
			.toList();

	public static final List<String[]> BARIS_RUTE = RUTE.stream()
			.map(rute -> new String[] { rute.id(), rute.asal(), rute.tujuan(), rute.jarak() })
			.toList();

	private MasterData() {
	}

}
