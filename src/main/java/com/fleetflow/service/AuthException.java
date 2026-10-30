package com.fleetflow.service;

/**
 * Kesalahan yang aman ditampilkan langsung ke pengguna (validasi / kredensial salah).
 */
public class AuthException extends RuntimeException {

	public AuthException(String message) {
		super(message);
	}

}
