package com.fleetflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Driver sesuai tabel {@code driver}.
 *
 * <p>{@code kotaSaatIniId} adalah posisi truk saat ini. Saat driver di-dispatch,
 * nilai ini dan {@code status} akan diperbarui.
 *
 * <p>Berat muatan tidak disimpan di sini maupun di produk, melainkan per
 * delivery order yang diinput oleh role operasional.
 *
 * <p>Kelas ini dipetakan Hibernate ke tabel {@code driver}.
 */
@Entity
@Table(name = "driver")
public class Driver {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "nama", nullable = false, length = 100)
	private String nama;

	@Column(name = "no_telepon", nullable = false, length = 30)
	private String noTelepon;

	@Column(name = "nomor_fleet", nullable = false, length = 30)
	private String nomorFleet;

	@Column(name = "kapasitas_ton", nullable = false)
	private int kapasitasTon;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private StatusDriver status;

	@Column(name = "kota_saat_ini_id", nullable = false, length = 10)
	private String kotaSaatIniId;

	/** Konstruktor tanpa argumen yang dibutuhkan Hibernate. */
	protected Driver() {
	}

	public Driver(String id, String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaSaatIniId) {
		this.id = id;
		this.nama = nama;
		this.noTelepon = noTelepon;
		this.nomorFleet = nomorFleet;
		this.kapasitasTon = kapasitasTon;
		this.status = status;
		this.kotaSaatIniId = kotaSaatIniId;
	}

	public String id() {
		return id;
	}

	public String nama() {
		return nama;
	}

	public String noTelepon() {
		return noTelepon;
	}

	public String nomorFleet() {
		return nomorFleet;
	}

	public int kapasitasTon() {
		return kapasitasTon;
	}

	public StatusDriver status() {
		return status;
	}

	public String kotaSaatIniId() {
		return kotaSaatIniId;
	}

	@Override
	public boolean equals(Object lain) {
		if (this == lain) {
			return true;
		}
		if (!(lain instanceof Driver driver)) {
			return false;
		}
		return id != null && id.equals(driver.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
