package com.fleetflow.domain;

/**
 * Pelanggan sesuai tabel {@code pelanggan} yang terhubung ke {@code kota}.
 */
public record Pelanggan(String id, String nama, String alamat, String kotaId) {
}
