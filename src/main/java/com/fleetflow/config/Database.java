package com.fleetflow.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Koneksi PostgreSQL berdasarkan berkas {@code application.properties}.
 *
 * <p>Driver JDBC PostgreSQL didaftarkan otomatis lewat dependency
 * {@code org.postgresql:postgresql} di {@code pom.xml}.
 */
public final class Database {

	private static final String NAMA_FILE = "/application.properties";
	private static final String URL_BAWAAN = "jdbc:postgresql://localhost:5432/fleetflow";

	private final String url;
	private final String username;
	private final String password;

	private Database(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * Membaca konfigurasi dari {@code application.properties} di classpath.
	 */
	public static Database dariKonfigurasi() {
		Properties properti = new Properties();
		try (InputStream masuk = Database.class.getResourceAsStream(NAMA_FILE)) {
			if (masuk == null) {
				throw new IllegalStateException("Berkas " + NAMA_FILE + " tidak ditemukan di classpath.");
			}
			properti.load(masuk);
		}
		catch (IOException e) {
			throw new UncheckedIOException("Gagal membaca " + NAMA_FILE, e);
		}

		return new Database(
				properti.getProperty("db.url", URL_BAWAAN),
				properti.getProperty("db.username", "postgres"),
				properti.getProperty("db.password", ""));
	}

	/**
	 * Membuka koneksi baru ke PostgreSQL.
	 */
	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Uji koneksi singkat, berguna untuk menampilkan status koneksi di aplikasi.
	 */
	public boolean terhubung() {
		try (Connection koneksi = connect()) {
			return koneksi.isValid(5);
		}
		catch (SQLException e) {
			return false;
		}
	}

	public String url() {
		return url;
	}

	public String username() {
		return username;
	}

}
