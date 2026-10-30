package com.fleetflow.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.fleetflow.domain.Role;
import com.fleetflow.domain.User;
import com.fleetflow.ui.master.MasterData;

/**
 * Halaman utama setelah login: header, menu sesuai role, dan isi menu.
 */
public class DashboardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final MainFrame frame;
	private final User user;
	private final JPanel isi = new JPanel(new CardLayout());
	private final Map<String, JToggleButton> menu = new LinkedHashMap<>();

	public DashboardPanel(SessionContext session, MainFrame frame) {
		super(new BorderLayout());
		this.frame = frame;
		this.user = session.getCurrentUser();

		if (user == null) {
			add(new JLabel("Sesi tidak ditemukan, silakan login kembali."));
			return;
		}

		siapkanIsi();
		add(buatHeader(), BorderLayout.NORTH);
		add(buatSidebar(), BorderLayout.WEST);
		add(isi, BorderLayout.CENTER);
		bukaMenu("Dashboard");
	}

	private void siapkanIsi() {
		tambahKartu("Dashboard", buatRingkasan());

		switch (user.role()) {
			case ADMIN -> {
				tambahKartu("Driver", new TabelPanel("Driver", MasterData.KOLOM_DRIVER, MasterData.BARIS_DRIVER));
				tambahKartu("Kota", new TabelPanel("Kota", MasterData.KOLOM_KOTA, MasterData.BARIS_KOTA));
				tambahKartu("Pelanggan",
						new TabelPanel("Pelanggan", MasterData.KOLOM_PELANGGAN, MasterData.BARIS_PELANGGAN));
				tambahKartu("Produk", new TabelPanel("Produk", MasterData.KOLOM_PRODUK, MasterData.BARIS_PRODUK));
				tambahKartu("Rute", new TabelPanel("Rute", MasterData.KOLOM_RUTE, MasterData.BARIS_RUTE));
			}
			case OPERASIONAL -> tambahKartu("Delivery Order", buatHalamanKosong("Belum ada data delivery order."));
			case DISPATCHER -> tambahKartu("Penugasan", buatHalamanKosong("Belum ada data penugasan."));
		}
	}

	private void tambahKartu(String nama, JComponent komponen) {
		isi.add(komponen, nama);
	}

	private void bukaMenu(String nama) {
		JToggleButton tombol = menu.get(nama);
		if (tombol != null) {
			tombol.setSelected(true);
		}
		((CardLayout) isi.getLayout()).show(isi, nama);
	}

	private JComponent buatHeader() {
		JLabel judul = new JLabel("FleetFlow");
		judul.setFont(judul.getFont().deriveFont(Font.BOLD, 16f));

		JPanel kiri = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		kiri.add(judul);

		JButton keluar = new JButton("Keluar");
		keluar.addActionListener(e -> konfirmasiKeluar());

		JPanel kanan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		kanan.add(new JLabel(user.username() + " - " + user.role().getLabel()));
		kanan.add(keluar);

		JPanel header = new JPanel(new BorderLayout());
		header.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		header.add(kiri, BorderLayout.WEST);
		header.add(kanan, BorderLayout.EAST);
		return header;
	}

	private JComponent buatSidebar() {
		JPanel sidebar = new JPanel();
		sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
		sidebar.setPreferredSize(new Dimension(170, 0));
		sidebar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel label = new JLabel("MENU");
		label.setFont(label.getFont().deriveFont(Font.BOLD, 11f));
		label.setAlignmentX(LEFT_ALIGNMENT);
		sidebar.add(label);
		sidebar.add(Box.createVerticalStrut(8));

		ButtonGroup grup = new ButtonGroup();
		for (String judul : judulMenu()) {
			JToggleButton tombol = new JToggleButton(judul);
			tombol.setAlignmentX(LEFT_ALIGNMENT);
			tombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
			tombol.setHorizontalAlignment(SwingConstants.LEFT);
			tombol.addActionListener(e -> bukaMenu(judul));
			grup.add(tombol);
			sidebar.add(tombol);
			sidebar.add(Box.createVerticalStrut(4));
			menu.put(judul, tombol);
		}

		sidebar.add(Box.createVerticalGlue());
		return sidebar;
	}

	private List<String> judulMenu() {
		return switch (user.role()) {
			case ADMIN -> List.of("Dashboard", "Driver", "Kota", "Pelanggan", "Produk", "Rute");
			case OPERASIONAL -> List.of("Dashboard", "Delivery Order");
			case DISPATCHER -> List.of("Dashboard", "Penugasan");
		};
	}

	private JComponent buatRingkasan() {
		JPanel halaman = new JPanel();
		halaman.setLayout(new BoxLayout(halaman, BoxLayout.Y_AXIS));
		halaman.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

		JLabel judul = new JLabel("Dashboard");
		judul.setFont(judul.getFont().deriveFont(Font.BOLD, 18f));
		judul.setAlignmentX(LEFT_ALIGNMENT);
		halaman.add(judul);
		halaman.add(Box.createVerticalStrut(10));

		for (String baris : List.of(
				"Selamat datang, " + user.username(),
				deskripsi(user.role()),
				" ",
				"Menu tersedia:")) {
			halaman.add(new JLabel(baris));
		}

		for (String judulMenu : judulMenu()) {
			halaman.add(new JLabel("- " + judulMenu));
		}

		halaman.add(Box.createVerticalGlue());
		return halaman;
	}

	private JComponent buatHalamanKosong(String pesan) {
		JPanel halaman = new JPanel(new BorderLayout());
		halaman.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		halaman.add(new JLabel(pesan), BorderLayout.NORTH);
		return halaman;
	}

	private void konfirmasiKeluar() {
		int pilihan = JOptionPane.showConfirmDialog(this, "Keluar dari akun ini?", "Konfirmasi",
				JOptionPane.YES_NO_OPTION);
		if (pilihan == JOptionPane.YES_OPTION) {
			frame.keluar();
		}
	}

	private String deskripsi(Role role) {
		return switch (role) {
			case ADMIN -> "Kelola data master dan akun pengguna aplikasi FleetFlow.";
			case OPERASIONAL -> "Buat delivery order dan perbarui status pengiriman.";
			case DISPATCHER -> "Tugaskan driver dan kendaraan ke delivery order.";
		};
	}

}
