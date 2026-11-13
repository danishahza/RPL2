package com.fleetflow.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.Driver;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.Rute;
import com.fleetflow.domain.StatusDriver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class MasterDataDao {

	private final JpaDao database;

	public MasterDataDao(JpaDao database) {
		this.database = database;
		database.dalamTransaksi(this::siapkanDataAwal);
	}

	private void siapkanDataAwal(EntityManager em) {
		if (em.createQuery("select count(k) from Kota k", Long.class).getSingleResult() > 0) {
			return;
		}

		List<Kota> daftarKota = List.of(
				new Kota("1", "Cilegon"),
				new Kota("2", "Tangerang"),
				new Kota("3", "Jakarta"),
				new Kota("4", "Semarang"),
				new Kota("5", "Surabaya"),
				new Kota("6", "Lampung"),
				new Kota("7", "Palembang"),
				new Kota("8", "Jambi"),
				new Kota("9", "Pekanbaru"),
				new Kota("10", "Medan"));
		daftarKota.forEach(em::persist);
		Map<String, String> idKota = daftarKota.stream().collect(Collectors.toMap(Kota::nama, Kota::id));

		List.of(
				new Driver("1", "Budi Santoso", "081200000001", "FL-001", 60, StatusDriver.TERSEDIA, idKota.get("Jakarta")),
				new Driver("2", "Andi Pratama", "081200000002", "FL-002", 60, StatusDriver.TERSEDIA, idKota.get("Jakarta")),
				new Driver("3", "Joko Susilo", "081200000003", "FL-003", 60, StatusDriver.TERSEDIA, idKota.get("Cilegon")),
				new Driver("4", "Rudi Hartono", "081200000004", "FL-004", 60, StatusDriver.TERSEDIA, idKota.get("Surabaya")))
				.forEach(em::persist);

		List.of(
				new Pelanggan("1", "PT Cilegon Industri", "Jl. Industri No. 1", idKota.get("Cilegon")),
				new Pelanggan("2", "PT Tangerang Makmur", "Jl. Raya No. 10", idKota.get("Tangerang")),
				new Pelanggan("3", "PT Jakarta Sentosa", "Jl. Sudirman No. 15", idKota.get("Jakarta")),
				new Pelanggan("4", "PT Semarang Sejahtera", "Jl. Pemuda No. 20", idKota.get("Semarang")),
				new Pelanggan("5", "PT Surabaya Logistik", "Jl. Pelabuhan No. 5", idKota.get("Surabaya")),
				new Pelanggan("6", "PT Lampung Niaga", "Jl. Lintas Sumatera No. 8", idKota.get("Lampung")),
				new Pelanggan("7", "PT Palembang Abadi", "Jl. Merdeka No. 12", idKota.get("Palembang")),
				new Pelanggan("8", "PT Jambi Bersama", "Jl. Gatot Subroto No. 7", idKota.get("Jambi")),
				new Pelanggan("9", "PT Pekanbaru Utama", "Jl. Jenderal Sudirman No. 25", idKota.get("Pekanbaru")),
				new Pelanggan("10", "PT Medan Distribusi", "Jl. Sisingamangaraja No. 18", idKota.get("Medan")))
				.forEach(em::persist);

		List.of(
				new Produk("1", "Semen"),
				new Produk("2", "Beras"),
				new Produk("3", "Pupuk"))
				.forEach(em::persist);

		List.of(
				rute("1", "Cilegon", "Tangerang", 30, idKota),
				rute("2", "Cilegon", "Jakarta", 95, idKota),
				rute("3", "Cilegon", "Semarang", 525, idKota),
				rute("4", "Cilegon", "Surabaya", 880, idKota),
				rute("5", "Cilegon", "Lampung", 125, idKota),
				rute("6", "Cilegon", "Palembang", 420, idKota),
				rute("7", "Cilegon", "Jambi", 695, idKota),
				rute("8", "Cilegon", "Pekanbaru", 1385, idKota),
				rute("9", "Cilegon", "Medan", 1785, idKota),
				rute("10", "Tangerang", "Jakarta", 80, idKota),
				rute("11", "Tangerang", "Semarang", 455, idKota),
				rute("12", "Tangerang", "Surabaya", 810, idKota),
				rute("13", "Tangerang", "Lampung", 205, idKota),
				rute("14", "Tangerang", "Palembang", 500, idKota),
				rute("15", "Tangerang", "Jambi", 775, idKota),
				rute("16", "Tangerang", "Pekanbaru", 1465, idKota),
				rute("17", "Tangerang", "Medan", 1865, idKota),
				rute("18", "Jakarta", "Semarang", 430, idKota),
				rute("19", "Jakarta", "Surabaya", 785, idKota),
				rute("20", "Jakarta", "Lampung", 225, idKota),
				rute("21", "Jakarta", "Palembang", 520, idKota),
				rute("22", "Jakarta", "Jambi", 795, idKota),
				rute("23", "Jakarta", "Pekanbaru", 1485, idKota),
				rute("24", "Jakarta", "Medan", 1885, idKota),
				rute("25", "Semarang", "Surabaya", 355, idKota),
				rute("26", "Semarang", "Lampung", 670, idKota),
				rute("27", "Semarang", "Palembang", 950, idKota),
				rute("28", "Semarang", "Jambi", 1215, idKota),
				rute("29", "Semarang", "Pekanbaru", 1875, idKota),
				rute("30", "Semarang", "Medan", 2315, idKota),
				rute("31", "Surabaya", "Lampung", 1140, idKota),
				rute("32", "Surabaya", "Palembang", 1305, idKota),
				rute("33", "Surabaya", "Jambi", 1570, idKota),
				rute("34", "Surabaya", "Pekanbaru", 2030, idKota),
				rute("35", "Surabaya", "Medan", 2660, idKota),
				rute("36", "Lampung", "Palembang", 285, idKota),
				rute("37", "Lampung", "Jambi", 560, idKota),
				rute("38", "Lampung", "Pekanbaru", 1250, idKota),
				rute("39", "Lampung", "Medan", 1650, idKota),
				rute("40", "Palembang", "Jambi", 275, idKota),
				rute("41", "Palembang", "Pekanbaru", 965, idKota),
				rute("42", "Palembang", "Medan", 1365, idKota),
				rute("43", "Jambi", "Pekanbaru", 690, idKota),
				rute("44", "Jambi", "Medan", 1090, idKota),
				rute("45", "Pekanbaru", "Medan", 645, idKota))
				.forEach(em::persist);
	}

	private static Rute rute(String id, String kotaA, String kotaB, int jarakKm, Map<String, String> idKota) {
		return new Rute(id, idKota.get(kotaA), idKota.get(kotaB), jarakKm);
	}

	public List<Kota> kota() {
		return urut(database.baca(em -> em.createQuery("select k from Kota k", Kota.class).getResultList()), Kota::id);
	}

	public List<Driver> driver() {
		return urut(database.baca(em -> em.createQuery("select d from Driver d", Driver.class).getResultList()),
				Driver::id);
	}

	public List<Pelanggan> pelanggan() {
		return urut(database.baca(em -> em.createQuery("select p from Pelanggan p", Pelanggan.class).getResultList()),
				Pelanggan::id);
	}

	public List<Produk> produk() {
		return urut(database.baca(em -> em.createQuery("select p from Produk p", Produk.class).getResultList()),
				Produk::id);
	}

	public List<Rute> rute() {
		return urut(database.baca(em -> em.createQuery("select r from Rute r", Rute.class).getResultList()), Rute::id);
	}

	public String namaKota(String id) {
		if (id == null) {
			return "-";
		}
		Kota kota = database.baca(em -> em.find(Kota.class, id));
		return kota == null ? "-" : kota.nama();
	}

	public String namaPelanggan(String id) {
		if (id == null) {
			return "-";
		}
		Pelanggan pelanggan = database.baca(em -> em.find(Pelanggan.class, id));
		return pelanggan == null ? "-" : pelanggan.nama();
	}

	public String namaProduk(String id) {
		if (id == null) {
			return "-";
		}
		Produk produk = database.baca(em -> em.find(Produk.class, id));
		return produk == null ? "-" : produk.nama();
	}

	// ----- Kota -----

	public void tambahKota(String nama) {
		String namaBaru = wajib(nama, "Nama kota");
		database.dalamTransaksi(em -> {
			cekNamaKotaUnik(em, namaBaru, null);
			em.persist(new Kota(idBerikut(em, "select k.id from Kota k"), namaBaru));
		});
	}

	public void ubahKota(String id, String nama) {
		String namaBaru = wajib(nama, "Nama kota");
		database.dalamTransaksi(em -> {
			Kota lama = cariKota(em, id);
			cekNamaKotaUnik(em, namaBaru, id);
			em.merge(new Kota(lama.id(), namaBaru));
		});
	}

	public void hapusKota(String id) {
		database.dalamTransaksi(em -> {
			Kota kota = cariKota(em, id);
			if (hitung(em, "select count(d) from Driver d where d.kotaSaatIniId = :nilai", id) > 0) {
				throw new IllegalArgumentException("Kota " + kota.nama() + " masih dipakai oleh data driver.");
			}
			if (hitung(em, "select count(p) from Pelanggan p where p.kotaId = :nilai", id) > 0) {
				throw new IllegalArgumentException("Kota " + kota.nama() + " masih dipakai oleh data pelanggan.");
			}
			if (hitung(em, "select count(r) from Rute r where r.kotaAId = :nilai or r.kotaBId = :nilai", id) > 0) {
				throw new IllegalArgumentException("Kota " + kota.nama() + " masih dipakai oleh data rute.");
			}
			em.remove(kota);
		});
	}

	// ----- Driver -----

	public void tambahDriver(String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		database.dalamTransaksi(em -> {
			Driver baru = new Driver(idBerikut(em, "select d.id from Driver d"), wajib(nama, "Nama"),
					wajib(noTelepon, "No. telepon"), wajib(nomorFleet, "No. fleet"), kapasitasTon, status, kotaId);
			validasiDriver(em, baru, null);
			em.persist(baru);
		});
	}

	public void ubahDriver(String id, String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		database.dalamTransaksi(em -> {
			Driver lama = cariDriver(em, id);
			Driver baru = new Driver(lama.id(), wajib(nama, "Nama"), wajib(noTelepon, "No. telepon"),
					wajib(nomorFleet, "No. fleet"), kapasitasTon, status, kotaId);
			validasiDriver(em, baru, id);
			em.merge(baru);
		});
	}

	public void hapusDriver(String id) {
		database.dalamTransaksi(em -> em.remove(cariDriver(em, id)));
	}

	// ----- Pelanggan -----

	public void tambahPelanggan(String nama, String alamat, String kotaId) {
		database.dalamTransaksi(em -> {
			Pelanggan baru = new Pelanggan(idBerikut(em, "select p.id from Pelanggan p"), wajib(nama, "Nama"),
					wajib(alamat, "Alamat"), kotaId);
			validasiPelanggan(em, baru);
			em.persist(baru);
		});
	}

	public void ubahPelanggan(String id, String nama, String alamat, String kotaId) {
		database.dalamTransaksi(em -> {
			Pelanggan lama = cariPelanggan(em, id);
			Pelanggan baru = new Pelanggan(lama.id(), wajib(nama, "Nama"), wajib(alamat, "Alamat"), kotaId);
			validasiPelanggan(em, baru);
			em.merge(baru);
		});
	}

	public void hapusPelanggan(String id) {
		database.dalamTransaksi(em -> em.remove(cariPelanggan(em, id)));
	}

	// ----- Produk -----

	public void tambahProduk(String nama) {
		String namaBaru = wajib(nama, "Nama produk");
		database.dalamTransaksi(em -> {
			cekNamaProdukUnik(em, namaBaru, null);
			em.persist(new Produk(idBerikut(em, "select p.id from Produk p"), namaBaru));
		});
	}

	public void ubahProduk(String id, String nama) {
		String namaBaru = wajib(nama, "Nama produk");
		database.dalamTransaksi(em -> {
			Produk lama = cariProduk(em, id);
			cekNamaProdukUnik(em, namaBaru, id);
			em.merge(new Produk(lama.id(), namaBaru));
		});
	}

	public void hapusProduk(String id) {
		database.dalamTransaksi(em -> em.remove(cariProduk(em, id)));
	}

	// ----- Rute -----

	public void tambahRute(String kotaAId, String kotaBId, int jarakKm) {
		database.dalamTransaksi(em -> {
			Rute baru = new Rute(idBerikut(em, "select r.id from Rute r"), kotaAId, kotaBId, jarakKm);
			validasiRute(em, baru, null);
			em.persist(baru);
		});
	}

	public void ubahRute(String id, String kotaAId, String kotaBId, int jarakKm) {
		database.dalamTransaksi(em -> {
			Rute lama = cariRute(em, id);
			Rute baru = new Rute(lama.id(), kotaAId, kotaBId, jarakKm);
			validasiRute(em, baru, id);
			em.merge(baru);
		});
	}

	public void hapusRute(String id) {
		database.dalamTransaksi(em -> em.remove(cariRute(em, id)));
	}

	// ----- Delivery Order -----

	public List<DeliveryOrder> deliveryOrder() {
		return urut(database.baca(em -> em.createQuery("select d from DeliveryOrder d", DeliveryOrder.class)
				.getResultList()), DeliveryOrder::id);
	}

	public void tambahDeliveryOrder(String kotaAsalId, String pengirimId, String kotaTujuanId, String penerimaId,
			LocalDate tanggalDo, LocalDate tanggalPickup, LocalDate tanggalTenggat, String produkId,
			double beratTon) {
		database.dalamTransaksi(em -> {
			String id = idBerikut(em, "select d.id from DeliveryOrder d");
			DeliveryOrder baru = new DeliveryOrder(id, String.format("DO-%04d", angkaId(id)), kotaAsalId, pengirimId,
					kotaTujuanId, penerimaId, tanggalDo, tanggalPickup, tanggalTenggat, produkId, beratTon);
			validasiDeliveryOrder(em, baru);
			em.persist(baru);
		});
	}

	public void ubahDeliveryOrder(String id, String kotaAsalId, String pengirimId, String kotaTujuanId,
			String penerimaId, LocalDate tanggalDo, LocalDate tanggalPickup, LocalDate tanggalTenggat,
			String produkId, double beratTon) {
		database.dalamTransaksi(em -> {
			DeliveryOrder lama = cariDeliveryOrder(em, id);
			DeliveryOrder baru = new DeliveryOrder(lama.id(), lama.nomorDo(), kotaAsalId, pengirimId, kotaTujuanId,
					penerimaId, tanggalDo, tanggalPickup, tanggalTenggat, produkId, beratTon);
			validasiDeliveryOrder(em, baru);
			em.merge(baru);
		});
	}

	public void hapusDeliveryOrder(String id) {
		database.dalamTransaksi(em -> em.remove(cariDeliveryOrder(em, id)));
	}

	// ----- Validasi dan util -----

	private void validasiDriver(EntityManager em, Driver driver, String idDikecualikan) {
		if (driver.kapasitasTon() <= 0) {
			throw new IllegalArgumentException("Kapasitas harus lebih dari 0 ton.");
		}
		if (driver.status() == null) {
			throw new IllegalArgumentException("Status driver wajib dipilih.");
		}
		if (driver.kotaSaatIniId() == null || em.find(Kota.class, driver.kotaSaatIniId()) == null) {
			throw new IllegalArgumentException("Kota saat ini wajib dipilih.");
		}
		String jpql = "select count(d) from Driver d where lower(d.nomorFleet) = lower(:fleet)"
				+ (idDikecualikan == null ? "" : " and d.id <> :id");
		TypedQuery<Long> query = em.createQuery(jpql, Long.class).setParameter("fleet", driver.nomorFleet());
		if (idDikecualikan != null) {
			query.setParameter("id", idDikecualikan);
		}
		if (query.getSingleResult() > 0) {
			throw new IllegalArgumentException("No. fleet " + driver.nomorFleet() + " sudah dipakai driver lain.");
		}
	}

	private void validasiPelanggan(EntityManager em, Pelanggan pelanggan) {
		if (pelanggan.kotaId() == null || em.find(Kota.class, pelanggan.kotaId()) == null) {
			throw new IllegalArgumentException("Kota pelanggan wajib dipilih.");
		}
	}

	private void validasiDeliveryOrder(EntityManager em, DeliveryOrder deliveryOrder) {
		if (deliveryOrder.kotaAsalId() == null || em.find(Kota.class, deliveryOrder.kotaAsalId()) == null) {
			throw new IllegalArgumentException("Kota asal wajib dipilih.");
		}
		if (deliveryOrder.kotaTujuanId() == null || em.find(Kota.class, deliveryOrder.kotaTujuanId()) == null) {
			throw new IllegalArgumentException("Kota tujuan wajib dipilih.");
		}
		if (deliveryOrder.kotaAsalId().equals(deliveryOrder.kotaTujuanId())) {
			throw new IllegalArgumentException("Kota asal dan kota tujuan tidak boleh sama.");
		}

		if (deliveryOrder.pengirimId() == null) {
			throw new IllegalArgumentException("Pengirim wajib dipilih.");
		}
		Pelanggan pengirim = em.find(Pelanggan.class, deliveryOrder.pengirimId());
		if (pengirim == null) {
			throw new IllegalArgumentException("Pengirim wajib dipilih.");
		}
		if (!pengirim.kotaId().equals(deliveryOrder.kotaAsalId())) {
			throw new IllegalArgumentException("Pengirim harus perusahaan yang berada di kota asal.");
		}

		if (deliveryOrder.penerimaId() == null) {
			throw new IllegalArgumentException("Penerima wajib dipilih.");
		}
		Pelanggan penerima = em.find(Pelanggan.class, deliveryOrder.penerimaId());
		if (penerima == null) {
			throw new IllegalArgumentException("Penerima wajib dipilih.");
		}
		if (!penerima.kotaId().equals(deliveryOrder.kotaTujuanId())) {
			throw new IllegalArgumentException("Penerima harus perusahaan yang berada di kota tujuan.");
		}

		if (deliveryOrder.produkId() == null || em.find(Produk.class, deliveryOrder.produkId()) == null) {
			throw new IllegalArgumentException("Produk wajib dipilih.");
		}
		if (!Double.isFinite(deliveryOrder.beratTon()) || deliveryOrder.beratTon() <= 0) {
			throw new IllegalArgumentException("Berat muatan harus lebih dari 0 ton.");
		}

		if (deliveryOrder.tanggalDo() == null) {
			throw new IllegalArgumentException("Tanggal pembuatan DO wajib diisi.");
		}
		if (deliveryOrder.tanggalPickup() == null) {
			throw new IllegalArgumentException("Jadwal pickup wajib diisi.");
		}
		if (deliveryOrder.tanggalTenggat() == null) {
			throw new IllegalArgumentException("Tanggal tenggat waktu pengiriman wajib diisi.");
		}
		if (deliveryOrder.tanggalPickup().isBefore(deliveryOrder.tanggalDo())) {
			throw new IllegalArgumentException("Jadwal pickup tidak boleh sebelum tanggal pembuatan DO.");
		}
		if (deliveryOrder.tanggalTenggat().isBefore(deliveryOrder.tanggalPickup())) {
			throw new IllegalArgumentException("Tenggat waktu pengiriman tidak boleh sebelum jadwal pickup.");
		}
	}

	private void validasiRute(EntityManager em, Rute rute, String idDikecualikan) {
		if (rute.kotaAId() == null || rute.kotaBId() == null) {
			throw new IllegalArgumentException("Kota asal dan kota tujuan wajib dipilih.");
		}
		if (rute.kotaAId().equals(rute.kotaBId())) {
			throw new IllegalArgumentException("Kota asal dan kota tujuan tidak boleh sama.");
		}
		if (rute.jarakKm() <= 0) {
			throw new IllegalArgumentException("Jarak harus lebih dari 0 km.");
		}
		String jpql = "select count(r) from Rute r where ((r.kotaAId = :a and r.kotaBId = :b)"
				+ " or (r.kotaAId = :b and r.kotaBId = :a))" + (idDikecualikan == null ? "" : " and r.id <> :id");
		TypedQuery<Long> query = em.createQuery(jpql, Long.class)
				.setParameter("a", rute.kotaAId())
				.setParameter("b", rute.kotaBId());
		if (idDikecualikan != null) {
			query.setParameter("id", idDikecualikan);
		}
		if (query.getSingleResult() > 0) {
			throw new IllegalArgumentException("Rute antara kedua kota tersebut sudah ada.");
		}
	}

	private void cekNamaKotaUnik(EntityManager em, String nama, String idDikecualikan) {
		boolean dipakai = adaData(em, "select count(k) from Kota k where lower(k.nama) = lower(:nama)", nama,
				idDikecualikan, "k.id");
		if (dipakai) {
			throw new IllegalArgumentException("Kota " + nama + " sudah ada.");
		}
	}

	private void cekNamaProdukUnik(EntityManager em, String nama, String idDikecualikan) {
		boolean dipakai = adaData(em, "select count(p) from Produk p where lower(p.nama) = lower(:nama)", nama,
				idDikecualikan, "p.id");
		if (dipakai) {
			throw new IllegalArgumentException("Produk " + nama + " sudah ada.");
		}
	}

	/** Menghitung baris dengan nama yang sama, kecuali ID yang sedang diubah. */
	private static boolean adaData(EntityManager em, String jpql, String nama, String idDikecualikan,
			String kolomId) {
		String kueri = jpql + (idDikecualikan == null ? "" : " and " + kolomId + " <> :id");
		TypedQuery<Long> query = em.createQuery(kueri, Long.class).setParameter("nama", nama);
		if (idDikecualikan != null) {
			query.setParameter("id", idDikecualikan);
		}
		return query.getSingleResult() > 0;
	}

	private static long hitung(EntityManager em, String jpql, String nilai) {
		return em.createQuery(jpql, Long.class).setParameter("nilai", nilai).getSingleResult();
	}

	private static Kota cariKota(EntityManager em, String id) {
		Kota kota = em.find(Kota.class, id);
		if (kota == null) {
			throw new IllegalArgumentException("Kota tidak ditemukan.");
		}
		return kota;
	}

	private static Driver cariDriver(EntityManager em, String id) {
		Driver driver = em.find(Driver.class, id);
		if (driver == null) {
			throw new IllegalArgumentException("Driver tidak ditemukan.");
		}
		return driver;
	}

	private static Pelanggan cariPelanggan(EntityManager em, String id) {
		Pelanggan pelanggan = em.find(Pelanggan.class, id);
		if (pelanggan == null) {
			throw new IllegalArgumentException("Pelanggan tidak ditemukan.");
		}
		return pelanggan;
	}

	private static Produk cariProduk(EntityManager em, String id) {
		Produk produk = em.find(Produk.class, id);
		if (produk == null) {
			throw new IllegalArgumentException("Produk tidak ditemukan.");
		}
		return produk;
	}

	private static Rute cariRute(EntityManager em, String id) {
		Rute rute = em.find(Rute.class, id);
		if (rute == null) {
			throw new IllegalArgumentException("Rute tidak ditemukan.");
		}
		return rute;
	}

	private static DeliveryOrder cariDeliveryOrder(EntityManager em, String id) {
		DeliveryOrder deliveryOrder = em.find(DeliveryOrder.class, id);
		if (deliveryOrder == null) {
			throw new IllegalArgumentException("Delivery order tidak ditemukan.");
		}
		return deliveryOrder;
	}

	private static String idBerikut(EntityManager em, String jpqlId) {
		int maks = 0;
		for (String id : em.createQuery(jpqlId, String.class).getResultList()) {
			maks = Math.max(maks, angkaId(id));
		}
		return String.valueOf(maks + 1);
	}

	private static <T> List<T> urut(List<T> daftar, Function<T, String> id) {
		List<T> hasil = new ArrayList<>(daftar);
		hasil.sort(Comparator.comparingInt(item -> angkaId(id.apply(item))));
		return List.copyOf(hasil);
	}

	private static int angkaId(String id) {
		try {
			return Integer.parseInt(id);
		}
		catch (NumberFormatException e) {
			// ID non-angka diabaikan; data aplikasi memakai ID angka seperti SERIAL.
			return 0;
		}
	}

	private static String wajib(String nilai, String label) {
		if (nilai == null || nilai.isBlank()) {
			throw new IllegalArgumentException(label + " wajib diisi.");
		}
		return nilai.trim();
	}

}
