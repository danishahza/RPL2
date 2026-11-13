package com.fleetflow.domain;

/**
 * Produk sesuai tabel {@code produk}.
 *
 * <p>Produk hanya menyimpan nama. Berat muatan diinput oleh role operasional
 * pada setiap delivery order karena beratnya berbeda-beda.
 */
public record Produk(String id, String nama) {
}
