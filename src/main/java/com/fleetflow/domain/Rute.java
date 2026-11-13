package com.fleetflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Rute antarkota sesuai tabel {@code rute}.
 *
 * <p>Satu baris menyimpan satu arah; pencarian rute di aplikasi dilakukan dua arah.
 *
 * <p>Kelas ini dipetakan Hibernate ke tabel {@code rute}.
 */
@Entity
@Table(name = "rute")
public class Rute {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "kota_a_id", nullable = false, length = 10)
	private String kotaAId;

	@Column(name = "kota_b_id", nullable = false, length = 10)
	private String kotaBId;

	@Column(name = "jarak_km", nullable = false)
	private int jarakKm;

	/** Konstruktor tanpa argumen yang dibutuhkan Hibernate. */
	protected Rute() {
	}

	public Rute(String id, String kotaAId, String kotaBId, int jarakKm) {
		this.id = id;
		this.kotaAId = kotaAId;
		this.kotaBId = kotaBId;
		this.jarakKm = jarakKm;
	}

	public String id() {
		return id;
	}

	public String kotaAId() {
		return kotaAId;
	}

	public String kotaBId() {
		return kotaBId;
	}

	public int jarakKm() {
		return jarakKm;
	}

	@Override
	public boolean equals(Object lain) {
		if (this == lain) {
			return true;
		}
		if (!(lain instanceof Rute rute)) {
			return false;
		}
		return id != null && id.equals(rute.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
