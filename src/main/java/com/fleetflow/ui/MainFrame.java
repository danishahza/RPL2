package com.fleetflow.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fleetflow.controller.AuthController;
import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.User;

/**
 * Jendela utama aplikasi. Isinya berganti antara login, registrasi, dan dashboard.
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final AuthController authController;
	private final MasterDataController masterDataController;
	private final SessionContext session;

	public MainFrame(AuthController authController, MasterDataController masterDataController, SessionContext session) {
		super("FleetFlow");
		this.authController = authController;
		this.masterDataController = masterDataController;
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
		tampilkan(new LoginPanel(authController, this, pesan));
	}

	public void tampilkanRegister() {
		tampilkan(new RegisterPanel(authController, this));
	}

	public void masuk(User user) {
		session.login(user);
		tampilkan(new DashboardPanel(session, this, masterDataController));
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
