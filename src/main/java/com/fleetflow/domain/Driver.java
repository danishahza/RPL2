package com.fleetflow.domain;

/**
 * Driver sesuai tabel {@code driver}.
 *
 * <p>{@code kotaSaatIniId} adalah posisi truk saat ini. Saat driver di-dispatch,
 * nilai ini dan {@code status} akan diperbarui.
 *
 * <p>Berat muatan tidak disimpan di sini maupun di produk, melainkan per
 * delivery order yang diinput oleh role operasional.
 */
public record Driver(String id, String nama, String noTelepon, String nomorFleet, int kapasitasTon,
		StatusDriver status, String kotaSaatIniId) {
}
