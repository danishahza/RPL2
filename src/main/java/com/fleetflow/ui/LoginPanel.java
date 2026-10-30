package com.fleetflow.ui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fleetflow.domain.User;
import com.fleetflow.service.AuthException;
import com.fleetflow.service.AuthService;

/**
 * Form login sederhana.
 */
public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final AuthService authService;
	private final MainFrame frame;

	private final JTextField username = new JTextField(16);
	private final JPasswordField password = new JPasswordField(16);
	private final JLabel pesan = new JLabel(" ");

	public LoginPanel(AuthService authService, MainFrame frame, String flash) {
		super(new GridBagLayout());
		this.authService = authService;
		this.frame = frame;

		JPanel form = new JPanel(new GridBagLayout());

		JLabel judul = new JLabel("FleetFlow");
		judul.setFont(judul.getFont().deriveFont(Font.BOLD, 20f));
		taruh(form, judul, 0, 0, 2);

		taruh(form, new JLabel("Sistem Transportasi Logistik"), 0, 1, 2);
		taruh(form, new JLabel("Username"), 0, 2, 1);
		taruh(form, username, 1, 2, 1);
		taruh(form, new JLabel("Password"), 0, 3, 1);
		taruh(form, password, 1, 3, 1);
		taruh(form, pesan, 0, 4, 2);

		JButton masuk = new JButton("Masuk");
		masuk.addActionListener(e -> handleLogin());

		JButton daftar = new JButton("Daftar");
		daftar.addActionListener(e -> frame.tampilkanRegister());

		JButton bersihkan = new JButton("Bersihkan");
		bersihkan.addActionListener(e -> bersihkan());

		JPanel tombol = new JPanel();
		tombol.add(masuk);
		tombol.add(daftar);
		tombol.add(bersihkan);
		taruh(form, tombol, 0, 5, 2);

		add(form);

		username.addActionListener(e -> password.requestFocusInWindow());
		password.addActionListener(e -> handleLogin());

		if (flash != null && !flash.isBlank()) {
			pesan.setText(flash);
		}
	}

	private void handleLogin() {
		try {
			User user = authService.login(username.getText(), new String(password.getPassword()));
			frame.masuk(user);
		}
		catch (AuthException e) {
			tampilkanPesan(e.getMessage());
		}
		catch (RuntimeException e) {
			tampilkanPesan("Terjadi kesalahan: " + e.getMessage());
		}
	}

	private void bersihkan() {
		username.setText("");
		password.setText("");
		pesan.setText(" ");
		pesan.setForeground(javax.swing.UIManager.getColor("Label.foreground"));
		username.requestFocusInWindow();
	}

	private void tampilkanPesan(String isi) {
		pesan.setForeground(Color.RED);
		pesan.setText(isi);
	}

	private void taruh(JPanel form, Component komponen, int kolom, int baris, int lebar) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = kolom;
		gbc.gridy = baris;
		gbc.gridwidth = lebar;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(3, 3, 3, 3);
		form.add(komponen, gbc);
	}

}
