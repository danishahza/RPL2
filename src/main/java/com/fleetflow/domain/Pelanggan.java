package com.fleetflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Pelanggan sesuai tabel {@code pelanggan} yang terhubung ke {@code kota}.
 *
 * <p>Kelas ini dipetakan Hibernate ke tabel {@code pelanggan}.
 */
@Entity
@Table(name = "pelanggan")
public class Pelanggan {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "nama", nullable = false, length = 100)
	private String nama;

	@Column(name = "alamat", nullable = false, length = 200)
	private String alamat;

	@Column(name = "kota_id", nullable = false, length = 10)
	private String kotaId;

	/** Konstruktor tanpa argumen yang dibutuhkan Hibernate. */
	protected Pelanggan() {
	}

	public Pelanggan(String id, String nama, String alamat, String kotaId) {
		this.id = id;
		this.nama = nama;
		this.alamat = alamat;
		this.kotaId = kotaId;
	}

	public String id() {
		return id;
	}

	public String nama() {
		return nama;
	}

	public String alamat() {
		return alamat;
	}

	public String kotaId() {
		return kotaId;
	}

	@Override
	public boolean equals(Object lain) {
		if (this == lain) {
			return true;
		}
		if (!(lain instanceof Pelanggan pelanggan)) {
			return false;
		}
		return id != null && id.equals(pelanggan.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
