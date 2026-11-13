package com.fleetflow.controller;

import org.springframework.stereotype.Controller;

import com.fleetflow.domain.Role;
import com.fleetflow.domain.User;
import com.fleetflow.service.AuthService;

@Controller
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	public User login(String username, String password) {
		return authService.login(username, password);
	}

	public User register(String username, String password, String confirmation, Role role) {
		return authService.register(username, password, confirmation, role);
	}
}
