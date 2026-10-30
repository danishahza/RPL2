package com.fleetflow.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fleetflow.domain.User;
import com.fleetflow.service.AuthService;

/**
 * Jendela utama aplikasi. Isinya berganti antara login, registrasi, dan dashboard.
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final AuthService authService;
	private final SessionContext session;

	public MainFrame(AuthService authService, SessionContext session) {
		super("FleetFlow");
		this.authService = authService;
		this.session = session;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(820, 520);
		setMinimumSize(new Dimension(700, 460));
		setLocationRelativeTo(null);

		tampilkanLogin();
	}

	public void tampilkanLogin() {
		tampilkanLogin(null);
	}

	public void tampilkanLogin(String pesan) {
		tampilkan(new LoginPanel(authService, this, pesan));
	}

	public void tampilkanRegister() {
		tampilkan(new RegisterPanel(authService, this));
	}

	public void masuk(User user) {
		session.login(user);
		tampilkan(new DashboardPanel(session, this));
	}

	public void keluar() {
		session.logout();
		tampilkanLogin();
	}

	private void tampilkan(JPanel panel) {
		setContentPane(panel);
		revalidate();
		repaint();
	}

}
