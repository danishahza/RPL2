package com.fleetflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Produk sesuai tabel {@code produk}.
 *
 * <p>Produk hanya menyimpan nama. Berat muatan diinput oleh role operasional
 * pada setiap delivery order karena beratnya berbeda-beda.
 *
 * <p>Kelas ini dipetakan Hibernate ke tabel {@code produk}.
 */
@Entity
@Table(name = "produk")
public class Produk {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "nama", nullable = false, length = 100)
	private String nama;

	/** Konstruktor tanpa argumen yang dibutuhkan Hibernate. */
	protected Produk() {
	}

	public Produk(String id, String nama) {
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
		if (!(lain instanceof Produk produk)) {
			return false;
		}
		return id != null && id.equals(produk.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
