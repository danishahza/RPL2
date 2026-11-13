package com.fleetflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Kota sesuai tabel {@code kota}.
 *
 * <p>Kelas ini dipetakan Hibernate ke tabel {@code kota}. Accessor tetap
 * {@code id()} dan {@code nama()} supaya pemakaian di UI tidak berubah.
 */
@Entity
@Table(name = "kota")
public class Kota {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "nama", nullable = false, length = 100)
	private String nama;

	/** Konstruktor tanpa argumen yang dibutuhkan Hibernate. */
	protected Kota() {
	}

	public Kota(String id, String nama) {
		this.id = id;
		this.nama = nama;
	}

	public String id() {
		return id;
	}

	public String nama() {
		return nama;
	}

	@Override
	public boolean equals(Object lain) {
		if (this == lain) {
			return true;
		}
		if (!(lain instanceof Kota kota)) {
			return false;
		}
		return id != null && id.equals(kota.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
