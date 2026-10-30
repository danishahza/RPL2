package com.fleetflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fleetflow.domain.Role;
import com.fleetflow.domain.User;

class AuthServiceTest {

	@TempDir
	private Path tempDir;

	private AuthService service() {
		return new AuthService(tempDir.resolve("users.txt"));
	}

	@Test
	void akunDefaultBisaLoginDenganRoleMasingMasing() {
		AuthService auth = service();

		assertEquals(Role.ADMIN, auth.login("admin", "admin123").role());
		assertEquals(Role.OPERASIONAL, auth.login("operasional", "operasional123").role());
		assertEquals(Role.DISPATCHER, auth.login("dispatcher", "dispatcher123").role());
	}

	@Test
	void passwordSalahDitolak() {
		assertThrows(AuthException.class, () -> service().login("admin", "bukan-password"));
	}

	@Test
	void usernameTidakDikenalDitolak() {
		assertThrows(AuthException.class, () -> service().login("tidak-ada", "admin123"));
	}

	@Test
	void registrasiBerhasilDanPasswordTidakDisimpanSebagaiTeksBiasa() throws Exception {
		AuthService auth = service();

		User baru = auth.register("siti.aminah", "rahasia123", "rahasia123", Role.OPERASIONAL);

		assertEquals("siti.aminah", baru.username());
		assertEquals(Role.OPERASIONAL, auth.login("siti.aminah", "rahasia123").role());

		String isiFile = Files.readString(tempDir.resolve("users.txt"));
		assertTrue(isiFile.contains("siti.aminah"));
		assertTrue(!isiFile.contains("rahasia123"));
	}

	@Test
	void akunTetapAdaSetelahServiceDibuatUlang() {
		service().register("budi.test", "rahasia123", "rahasia123", Role.DISPATCHER);

		AuthService ulang = service();

		assertEquals(Role.DISPATCHER, ulang.login("budi.test", "rahasia123").role());
	}

	@Test
	void usernameDuplikatDitolak() {
		assertThrows(AuthException.class,
				() -> service().register("admin", "rahasia123", "rahasia123", Role.ADMIN));
	}

	@Test
	void konfirmasiBedaDitolak() {
		assertThrows(AuthException.class,
				() -> service().register("budi.test", "rahasia123", "rahasia321", Role.DISPATCHER));
	}

	@Test
	void passwordTerlaluPendekDitolak() {
		assertThrows(AuthException.class,
				() -> service().register("budi.test", "123", "123", Role.DISPATCHER));
	}

	@Test
	void usernameTidakValidDitolak() {
		assertThrows(AuthException.class,
				() -> service().register("ab", "rahasia123", "rahasia123", Role.DISPATCHER));
	}

}
