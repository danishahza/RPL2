package com.fleetflow.domain;

/**
 * Status driver saat ini (kolom {@code status} pada tabel {@code driver}).
 */
public enum StatusDriver {

	TERSEDIA("Tersedia"),
	PENJEMPUTAN("Penjemputan"),
	PENGANTARAN("Pengantaran");

	private final String label;

	StatusDriver(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

}
