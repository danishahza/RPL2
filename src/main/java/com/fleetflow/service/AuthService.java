package com.fleetflow.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.fleetflow.domain.Role;
import com.fleetflow.domain.User;

/**
 * Login dan registrasi akun.
 *
 * <p>Akun disimpan di satu file teks ({@code data/users.txt}), tanpa database.
 * Password di-hash memakai PBKDF2 dari JDK.
 */
public class AuthService {

	private static final Pattern USERNAME_VALID = Pattern.compile("^[a-zA-Z0-9._]{3,30}$");
	private static final int PASSWORD_MIN_LENGTH = 6;
	private static final int PBKDF2_ITERATIONS = 120_000;
	private static final int SALT_BYTES = 16;
	private static final int HASH_BITS = 256;

	private record Akun(String username, Role role, String salt, String hash) {
	}

	private final Path file;
	private final Map<String, Akun> akun = new LinkedHashMap<>();
	private final SecureRandom random = new SecureRandom();

	public AuthService() {
		this(Path.of("data", "users.txt"));
	}

	public AuthService(Path file) {
		this.file = file;
		muat();
	}

	/**
	 * Validasi kredensial. Mengembalikan user jika username dan password cocok.
	 */
	public synchronized User login(String username, String password) {
		if (username == null || username.isBlank() || password == null || password.isBlank()) {
			throw new AuthException("Username dan password wajib diisi.");
		}

		Akun data = akun.get(normalisasi(username));
		if (data == null || !cocok(password, data)) {
			throw new AuthException("Username atau password salah.");
		}
		return new User(data.username(), data.role());
	}

	/**
	 * Pendaftaran akun baru dengan salah satu dari 3 role.
	 */
	public synchronized User register(String username, String password, String konfirmasiPassword, Role role) {
		String userBaru = normalisasi(username);

		if (!USERNAME_VALID.matcher(userBaru).matches()) {
			throw new AuthException("Username 3-30 karakter, hanya huruf, angka, titik, dan garis bawah.");
		}
		if (password == null || password.length() < PASSWORD_MIN_LENGTH) {
			throw new AuthException("Password minimal " + PASSWORD_MIN_LENGTH + " karakter.");
		}
		if (!password.equals(konfirmasiPassword)) {
			throw new AuthException("Konfirmasi password tidak sama.");
		}
		if (role == null) {
			throw new AuthException("Role wajib dipilih.");
		}
		if (akun.containsKey(userBaru)) {
			throw new AuthException("Username sudah dipakai, pilih yang lain.");
		}

		String salt = acak();
		Akun baru = new Akun(userBaru, role, salt, hash(password, salt));
		simpan(baru);
		akun.put(userBaru, baru);
		return new User(userBaru, role);
	}

	private void muat() {
		try {
			if (Files.exists(file)) {
				for (String baris : Files.readAllLines(file, StandardCharsets.UTF_8)) {
					Akun data = parse(baris);
					if (data != null) {
						akun.put(data.username(), data);
					}
				}
			}
		}
		catch (IOException e) {
			throw new UncheckedIOException("Gagal membaca file akun: " + file, e);
		}

		if (akun.isEmpty()) {
			tambah("admin", "admin123", Role.ADMIN);
			tambah("operasional", "operasional123", Role.OPERASIONAL);
			tambah("dispatcher", "dispatcher123", Role.DISPATCHER);
			simpanSemua();
		}
	}

	private Akun parse(String baris) {
		String[] bagian = baris.split(";");
		if (bagian.length != 4) {
			return null;
		}
		try {
			return new Akun(bagian[0], Role.valueOf(bagian[1]), bagian[2], bagian[3]);
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}

	private void tambah(String username, String password, Role role) {
		String salt = acak();
		akun.put(username, new Akun(username, role, salt, hash(password, salt)));
	}

	private void simpan(Akun data) {
		tulis(baris(data) + System.lineSeparator(), true);
	}

	private void simpanSemua() {
		StringBuilder isi = new StringBuilder();
		for (Akun data : akun.values()) {
			isi.append(baris(data)).append(System.lineSeparator());
		}
		tulis(isi.toString(), false);
	}

	private void tulis(String isi, boolean tambah) {
		try {
			Path folder = file.toAbsolutePath().getParent();
			if (folder != null) {
				Files.createDirectories(folder);
			}
			if (tambah) {
				Files.writeString(file, isi, StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			}
			else {
				Files.writeString(file, isi, StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new UncheckedIOException("Gagal menyimpan file akun: " + file, e);
		}
	}

	private String baris(Akun data) {
		return data.username() + ";" + data.role().name() + ";" + data.salt() + ";" + data.hash();
	}

	private String hash(String password, String salt) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),
					Base64.getDecoder().decode(salt), PBKDF2_ITERATIONS, HASH_BITS);
			byte[] hasil = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
				.generateSecret(spec)
				.getEncoded();
			spec.clearPassword();
			return Base64.getEncoder().encodeToString(hasil);
		}
		catch (GeneralSecurityException e) {
			throw new IllegalStateException("Gagal membuat hash password", e);
		}
	}

	private boolean cocok(String password, Akun data) {
		byte[] input = Base64.getDecoder().decode(hash(password, data.salt()));
		byte[] tersimpan = Base64.getDecoder().decode(data.hash());
		return MessageDigest.isEqual(input, tersimpan);
	}

	private String acak() {
		byte[] salt = new byte[SALT_BYTES];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	private String normalisasi(String username) {
		return username == null ? "" : username.trim().toLowerCase(Locale.ROOT);
	}

}
