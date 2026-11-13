package com.fleetflow.domain;

/**
 * Rute antarkota sesuai tabel {@code rute}.
 *
 * <p>Satu baris menyimpan satu arah; pencarian rute di aplikasi dilakukan dua arah.
 */
public record Rute(String id, String kotaAId, String kotaBId, int jarakKm) {
}
