package com.fleetflow.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fleetflow.domain.Role;
import com.fleetflow.domain.User;
import com.fleetflow.service.AuthException;
import com.fleetflow.service.AuthService;

/**
 * Form pendaftaran akun sederhana.
 */
public class RegisterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final AuthService authService;
	private final MainFrame frame;

	private final JTextField username = new JTextField(16);
	private final JPasswordField password = new JPasswordField(16);
	private final JPasswordField konfirmasi = new JPasswordField(16);
	private final JComboBox<Role> role = new JComboBox<>(Role.values());
	private final JLabel pesan = new JLabel(" ");

	public RegisterPanel(AuthService authService, MainFrame frame) {
		super(new GridBagLayout());
		this.authService = authService;
		this.frame = frame;

		role.setSelectedItem(Role.OPERASIONAL);

		JPanel form = new JPanel(new GridBagLayout());

		JLabel judul = new JLabel("Buat Akun");
		judul.setFont(judul.getFont().deriveFont(Font.BOLD, 18f));
		taruh(form, judul, 0, 0, 2);

		taruh(form, new JLabel("Pilih role sesuai tugas Anda"), 0, 1, 2);
		taruh(form, new JLabel("Username"), 0, 2, 1);
		taruh(form, username, 1, 2, 1);
		taruh(form, new JLabel("Password"), 0, 3, 1);
		taruh(form, password, 1, 3, 1);
		taruh(form, new JLabel("Konfirmasi password"), 0, 4, 1);
		taruh(form, konfirmasi, 1, 4, 1);
		taruh(form, new JLabel("Role"), 0, 5, 1);
		taruh(form, role, 1, 5, 1);
		taruh(form, pesan, 0, 6, 2);

		JButton daftar = new JButton("Daftar");
		daftar.addActionListener(e -> handleDaftar());

		JButton kembali = new JButton("Kembali ke login");
		kembali.addActionListener(e -> frame.tampilkanLogin());

		JPanel tombol = new JPanel();
		tombol.add(daftar);
		tombol.add(kembali);
		taruh(form, tombol, 0, 7, 2);

		add(form);
	}

	private void handleDaftar() {
		try {
			User user = authService.register(
					username.getText(),
					new String(password.getPassword()),
					new String(konfirmasi.getPassword()),
					(Role) role.getSelectedItem());
			frame.tampilkanLogin("Akun '" + user.username() + "' berhasil dibuat. Silakan masuk.");
		}
		catch (AuthException e) {
			tampilkanPesan(e.getMessage());
		}
		catch (RuntimeException e) {
			tampilkanPesan("Terjadi kesalahan: " + e.getMessage());
		}
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
