package com.fleetflow.ui.master;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fleetflow.domain.Driver;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.Rute;
import com.fleetflow.domain.StatusDriver;

/**
 * Penyimpanan data master selama aplikasi berjalan.
 *
 * <p>Data awal mengikuti isi database FleetFlow. CRUD dilakukan lewat method di
 * kelas ini sehingga semua halaman admin memakai sumber data yang sama.
 */
public final class MasterData {

	private final List<Kota> daftarKota = new ArrayList<>();
	private final List<Driver> daftarDriver = new ArrayList<>();
	private final List<Pelanggan> daftarPelanggan = new ArrayList<>();
	private final List<Produk> daftarProduk = new ArrayList<>();
	private final List<Rute> daftarRute = new ArrayList<>();

	public MasterData() {
		daftarKota.addAll(List.of(
				new Kota("1", "Cilegon"),
				new Kota("2", "Tangerang"),
				new Kota("3", "Jakarta"),
				new Kota("4", "Semarang"),
				new Kota("5", "Surabaya"),
				new Kota("6", "Lampung"),
				new Kota("7", "Palembang"),
				new Kota("8", "Jambi"),
				new Kota("9", "Pekanbaru"),
				new Kota("10", "Medan")));

		daftarDriver.addAll(List.of(
				new Driver("1", "Budi Santoso", "081200000001", "FL-001", 60, StatusDriver.TERSEDIA, "3"),
				new Driver("2", "Andi Pratama", "081200000002", "FL-002", 60, StatusDriver.TERSEDIA, "3"),
				new Driver("3", "Joko Susilo", "081200000003", "FL-003", 60, StatusDriver.TERSEDIA, "1"),
				new Driver("4", "Rudi Hartono", "081200000004", "FL-004", 60, StatusDriver.TERSEDIA, "5")));

		daftarPelanggan.addAll(List.of(
				new Pelanggan("1", "PT Cilegon Industri", "Jl. Industri No. 1", "1"),
				new Pelanggan("2", "PT Tangerang Makmur", "Jl. Raya No. 10", "2"),
				new Pelanggan("3", "PT Jakarta Sentosa", "Jl. Sudirman No. 15", "3"),
				new Pelanggan("4", "PT Semarang Sejahtera", "Jl. Pemuda No. 20", "4"),
				new Pelanggan("5", "PT Surabaya Logistik", "Jl. Pelabuhan No. 5", "5"),
				new Pelanggan("6", "PT Lampung Niaga", "Jl. Lintas Sumatera No. 8", "6"),
				new Pelanggan("7", "PT Palembang Abadi", "Jl. Merdeka No. 12", "7"),
				new Pelanggan("8", "PT Jambi Bersama", "Jl. Gatot Subroto No. 7", "8"),
				new Pelanggan("9", "PT Pekanbaru Utama", "Jl. Jenderal Sudirman No. 25", "9"),
				new Pelanggan("10", "PT Medan Distribusi", "Jl. Sisingamangaraja No. 18", "10")));

		daftarProduk.addAll(List.of(
				new Produk("1", "Semen"),
				new Produk("2", "Beras"),
				new Produk("3", "Pupuk")));

		daftarRute.addAll(List.of(
				rute("1", "Cilegon", "Tangerang", 30),
				rute("2", "Cilegon", "Jakarta", 95),
				rute("3", "Cilegon", "Semarang", 525),
				rute("4", "Cilegon", "Surabaya", 880),
				rute("5", "Cilegon", "Lampung", 125),
				rute("6", "Cilegon", "Palembang", 420),
				rute("7", "Cilegon", "Jambi", 695),
				rute("8", "Cilegon", "Pekanbaru", 1385),
				rute("9", "Cilegon", "Medan", 1785),
				rute("10", "Tangerang", "Jakarta", 80),
				rute("11", "Tangerang", "Semarang", 455),
				rute("12", "Tangerang", "Surabaya", 810),
				rute("13", "Tangerang", "Lampung", 205),
				rute("14", "Tangerang", "Palembang", 500),
				rute("15", "Tangerang", "Jambi", 775),
				rute("16", "Tangerang", "Pekanbaru", 1465),
				rute("17", "Tangerang", "Medan", 1865),
				rute("18", "Jakarta", "Semarang", 430),
				rute("19", "Jakarta", "Surabaya", 785),
				rute("20", "Jakarta", "Lampung", 225),
				rute("21", "Jakarta", "Palembang", 520),
				rute("22", "Jakarta", "Jambi", 795),
				rute("23", "Jakarta", "Pekanbaru", 1485),
				rute("24", "Jakarta", "Medan", 1885),
				rute("25", "Semarang", "Surabaya", 355),
				rute("26", "Semarang", "Lampung", 670),
				rute("27", "Semarang", "Palembang", 950),
				rute("28", "Semarang", "Jambi", 1215),
				rute("29", "Semarang", "Pekanbaru", 1875),
				rute("30", "Semarang", "Medan", 2315),
				rute("31", "Surabaya", "Lampung", 1140),
				rute("32", "Surabaya", "Palembang", 1305),
				rute("33", "Surabaya", "Jambi", 1570),
				rute("34", "Surabaya", "Pekanbaru", 2030),
				rute("35", "Surabaya", "Medan", 2660),
				rute("36", "Lampung", "Palembang", 285),
				rute("37", "Lampung", "Jambi", 560),
				rute("38", "Lampung", "Pekanbaru", 1250),
				rute("39", "Lampung", "Medan", 1650),
				rute("40", "Palembang", "Jambi", 275),
				rute("41", "Palembang", "Pekanbaru", 965),
				rute("42", "Palembang", "Medan", 1365),
				rute("43", "Jambi", "Pekanbaru", 690),
				rute("44", "Jambi", "Medan", 1090),
				rute("45", "Pekanbaru", "Medan", 645)));
	}

	public List<Kota> kota() {
		return List.copyOf(daftarKota);
	}

	public List<Driver> driver() {
		return List.copyOf(daftarDriver);
	}

	public List<Pelanggan> pelanggan() {
		return List.copyOf(daftarPelanggan);
	}

	public List<Produk> produk() {
		return List.copyOf(daftarProduk);
	}

	public List<Rute> rute() {
		return List.copyOf(daftarRute);
	}

	public String namaKota(String id) {
		return daftarKota.stream()
				.filter(kota -> kota.id().equals(id))
				.map(Kota::nama)
				.findFirst()
				.orElse("-");
	}

	// ----- Kota -----

	public void tambahKota(String nama) {
		String namaBaru = wajib(nama, "Nama kota");
		cekNamaKotaUnik(namaBaru, null);
		daftarKota.add(new Kota(idBerikut(daftarKota, Kota::id), namaBaru));
	}

	public void ubahKota(String id, String nama) {
		Kota lama = cariKota(id);
		String namaBaru = wajib(nama, "Nama kota");
		cekNamaKotaUnik(namaBaru, id);
		daftarKota.set(daftarKota.indexOf(lama), new Kota(id, namaBaru));
	}

	public void hapusKota(String id) {
		Kota kota = cariKota(id);
		if (daftarDriver.stream().anyMatch(driver -> driver.kotaSaatIniId().equals(id))) {
			throw new IllegalArgumentException("Kota " + kota.nama() + " masih dipakai oleh data driver.");
		}
		if (daftarPelanggan.stream().anyMatch(pelanggan -> pelanggan.kotaId().equals(id))) {
			throw new IllegalArgumentException("Kota " + kota.nama() + " masih dipakai oleh data pelanggan.");
		}
		if (daftarRute.stream().anyMatch(rute -> rute.kotaAId().equals(id) || rute.kotaBId().equals(id))) {
			throw new IllegalArgumentException("Kota " + kota.nama() + " masih dipakai oleh data rute.");
		}
		daftarKota.remove(kota);
	}

	// ----- Driver -----

	public void tambahDriver(String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		Driver baru = new Driver(idBerikut(daftarDriver, Driver::id), wajib(nama, "Nama"),
				wajib(noTelepon, "No. telepon"), wajib(nomorFleet, "No. fleet"), kapasitasTon, status, kotaId);
		validasiDriver(baru, null);
		daftarDriver.add(baru);
	}

	public void ubahDriver(String id, String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		Driver lama = cariDriver(id);
		Driver baru = new Driver(id, wajib(nama, "Nama"), wajib(noTelepon, "No. telepon"),
				wajib(nomorFleet, "No. fleet"), kapasitasTon, status, kotaId);
		validasiDriver(baru, id);
		daftarDriver.set(daftarDriver.indexOf(lama), baru);
	}

	public void hapusDriver(String id) {
		daftarDriver.remove(cariDriver(id));
	}

	// ----- Pelanggan -----

	public void tambahPelanggan(String nama, String alamat, String kotaId) {
		Pelanggan baru = new Pelanggan(idBerikut(daftarPelanggan, Pelanggan::id), wajib(nama, "Nama"),
				wajib(alamat, "Alamat"), kotaId);
		validasiPelanggan(baru);
		daftarPelanggan.add(baru);
	}

	public void ubahPelanggan(String id, String nama, String alamat, String kotaId) {
		Pelanggan lama = cariPelanggan(id);
		Pelanggan baru = new Pelanggan(id, wajib(nama, "Nama"), wajib(alamat, "Alamat"), kotaId);
		validasiPelanggan(baru);
		daftarPelanggan.set(daftarPelanggan.indexOf(lama), baru);
	}

	public void hapusPelanggan(String id) {
		daftarPelanggan.remove(cariPelanggan(id));
	}

	// ----- Produk -----

	public void tambahProduk(String nama) {
		String namaBaru = wajib(nama, "Nama produk");
		cekNamaProdukUnik(namaBaru, null);
		daftarProduk.add(new Produk(idBerikut(daftarProduk, Produk::id), namaBaru));
	}

	public void ubahProduk(String id, String nama) {
		Produk lama = cariProduk(id);
		String namaBaru = wajib(nama, "Nama produk");
		cekNamaProdukUnik(namaBaru, id);
		daftarProduk.set(daftarProduk.indexOf(lama), new Produk(id, namaBaru));
	}

	public void hapusProduk(String id) {
		daftarProduk.remove(cariProduk(id));
	}

	// ----- Rute -----

	public void tambahRute(String kotaAId, String kotaBId, int jarakKm) {
		Rute baru = new Rute(idBerikut(daftarRute, Rute::id), kotaAId, kotaBId, jarakKm);
		validasiRute(baru, null);
		daftarRute.add(baru);
	}

	public void ubahRute(String id, String kotaAId, String kotaBId, int jarakKm) {
		Rute lama = cariRute(id);
		Rute baru = new Rute(id, kotaAId, kotaBId, jarakKm);
		validasiRute(baru, id);
		daftarRute.set(daftarRute.indexOf(lama), baru);
	}

	public void hapusRute(String id) {
		daftarRute.remove(cariRute(id));
	}

	// ----- Validasi dan util -----

	private void validasiDriver(Driver driver, String idDikecualikan) {
		if (driver.kapasitasTon() <= 0) {
			throw new IllegalArgumentException("Kapasitas harus lebih dari 0 ton.");
		}
		if (driver.status() == null) {
			throw new IllegalArgumentException("Status driver wajib dipilih.");
		}
		if (driver.kotaSaatIniId() == null || daftarKota.stream()
				.noneMatch(kota -> kota.id().equals(driver.kotaSaatIniId()))) {
			throw new IllegalArgumentException("Kota saat ini wajib dipilih.");
		}
		boolean fleetDipakai = daftarDriver.stream()
				.anyMatch(lain -> !lain.id().equals(idDikecualikan)
						&& lain.nomorFleet().equalsIgnoreCase(driver.nomorFleet()));
		if (fleetDipakai) {
			throw new IllegalArgumentException("No. fleet " + driver.nomorFleet() + " sudah dipakai driver lain.");
		}
	}

	private void validasiPelanggan(Pelanggan pelanggan) {
		if (pelanggan.kotaId() == null || daftarKota.stream()
				.noneMatch(kota -> kota.id().equals(pelanggan.kotaId()))) {
			throw new IllegalArgumentException("Kota pelanggan wajib dipilih.");
		}
	}

	private void validasiRute(Rute rute, String idDikecualikan) {
		if (rute.kotaAId() == null || rute.kotaBId() == null) {
			throw new IllegalArgumentException("Kota asal dan kota tujuan wajib dipilih.");
		}
		if (rute.kotaAId().equals(rute.kotaBId())) {
			throw new IllegalArgumentException("Kota asal dan kota tujuan tidak boleh sama.");
		}
		if (rute.jarakKm() <= 0) {
			throw new IllegalArgumentException("Jarak harus lebih dari 0 km.");
		}
		boolean duplikat = daftarRute.stream().anyMatch(lain -> !lain.id().equals(idDikecualikan)
				&& ((lain.kotaAId().equals(rute.kotaAId()) && lain.kotaBId().equals(rute.kotaBId()))
						|| (lain.kotaAId().equals(rute.kotaBId()) && lain.kotaBId().equals(rute.kotaAId()))));
		if (duplikat) {
			throw new IllegalArgumentException("Rute antara kedua kota tersebut sudah ada.");
		}
	}

	private void cekNamaKotaUnik(String nama, String idDikecualikan) {
		boolean dipakai = daftarKota.stream()
				.anyMatch(kota -> !kota.id().equals(idDikecualikan) && kota.nama().equalsIgnoreCase(nama));
		if (dipakai) {
			throw new IllegalArgumentException("Kota " + nama + " sudah ada.");
		}
	}

	private void cekNamaProdukUnik(String nama, String idDikecualikan) {
		boolean dipakai = daftarProduk.stream()
				.anyMatch(produk -> !produk.id().equals(idDikecualikan) && produk.nama().equalsIgnoreCase(nama));
		if (dipakai) {
			throw new IllegalArgumentException("Produk " + nama + " sudah ada.");
		}
	}

	private Kota cariKota(String id) {
		return daftarKota.stream().filter(kota -> kota.id().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Kota tidak ditemukan."));
	}

	private Driver cariDriver(String id) {
		return daftarDriver.stream().filter(driver -> driver.id().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Driver tidak ditemukan."));
	}

	private Pelanggan cariPelanggan(String id) {
		return daftarPelanggan.stream().filter(pelanggan -> pelanggan.id().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Pelanggan tidak ditemukan."));
	}

	private Produk cariProduk(String id) {
		return daftarProduk.stream().filter(produk -> produk.id().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Produk tidak ditemukan."));
	}

	private Rute cariRute(String id) {
		return daftarRute.stream().filter(rute -> rute.id().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Rute tidak ditemukan."));
	}

	private Rute rute(String id, String kotaA, String kotaB, int jarakKm) {
		return new Rute(id, idKota(kotaA), idKota(kotaB), jarakKm);
	}

	private String idKota(String nama) {
		return daftarKota.stream().filter(kota -> kota.nama().equals(nama)).map(Kota::id).findFirst()
				.orElseThrow(() -> new IllegalStateException("Kota tidak ditemukan: " + nama));
	}

	private static String wajib(String nilai, String label) {
		if (nilai == null || nilai.isBlank()) {
			throw new IllegalArgumentException(label + " wajib diisi.");
		}
		return nilai.trim();
	}

	private static <T> String idBerikut(List<T> daftar, Function<T, String> id) {
		int maks = 0;
		for (T item : daftar) {
			try {
				maks = Math.max(maks, Integer.parseInt(id.apply(item)));
			}
			catch (NumberFormatException e) {
				// ID non-angka diabaikan; data aplikasi memakai ID angka seperti SERIAL.
			}
		}
		return String.valueOf(maks + 1);
	}

}
