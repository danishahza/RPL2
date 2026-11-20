package com.fleetflow.ui.operasional;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Pemilih tanggal dengan popup kalender. Nilai hanya bisa dipilih dari
 * kalender, tidak bisa diketik manual.
 */
final class TanggalPicker extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Locale INDONESIA = Locale.forLanguageTag("id-ID");
	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy", INDONESIA);
	private static final String[] NAMA_HARI = { "Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min" };

	private final JTextField tampilan = new JTextField(10);
	private final JButton tombol = new JButton("Pilih");
	private final JPopupMenu popup = new JPopupMenu();
	private final JLabel judul = new JLabel("", SwingConstants.CENTER);
	private final JPanel panelHari = new JPanel(new GridLayout(0, 7, 2, 2));

	private LocalDate nilai;
	private YearMonth bulanTampil;

	TanggalPicker(LocalDate awal) {
		super(new BorderLayout(4, 0));
		this.nilai = awal;
		this.bulanTampil = YearMonth.from(awal);

		tampilan.setEditable(false);
		tampilan.setFocusable(false);
		tampilan.setHorizontalAlignment(SwingConstants.CENTER);
		tampilan.setText(FORMAT.format(awal));
		tombol.setMargin(new Insets(2, 8, 2, 8));

		add(tampilan, BorderLayout.CENTER);
		add(tombol, BorderLayout.EAST);

		popup.setLayout(new BorderLayout());
		popup.add(buatKalender(), BorderLayout.CENTER);
		tombol.addActionListener(e -> {
			bulanTampil = YearMonth.from(nilai);
			gambarHari();
			popup.show(tombol, 0, tombol.getHeight());
		});
	}

	LocalDate nilai() {
		return nilai;
	}

	private JPanel buatKalender() {
		JPanel panel = new JPanel(new BorderLayout(0, 4));
		panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		JButton sebelumnya = new JButton("<");
		JButton berikutnya = new JButton(">");
		sebelumnya.setMargin(new Insets(0, 6, 0, 6));
		berikutnya.setMargin(new Insets(0, 6, 0, 6));
		sebelumnya.addActionListener(e -> {
			bulanTampil = bulanTampil.minusMonths(1);
			gambarHari();
		});
		berikutnya.addActionListener(e -> {
			bulanTampil = bulanTampil.plusMonths(1);
			gambarHari();
		});

		JPanel header = new JPanel(new BorderLayout());
		header.add(sebelumnya, BorderLayout.WEST);
		header.add(judul, BorderLayout.CENTER);
		header.add(berikutnya, BorderLayout.EAST);
		panel.add(header, BorderLayout.NORTH);
		panel.add(panelHari, BorderLayout.CENTER);

		gambarHari();
		return panel;
	}

	private void gambarHari() {
		judul.setText(bulanTampil.getMonth().getDisplayName(TextStyle.FULL, INDONESIA) + " "
				+ bulanTampil.getYear());
		panelHari.removeAll();
		for (String hari : NAMA_HARI) {
			JLabel label = new JLabel(hari, SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(34, 22));
			panelHari.add(label);
		}

		int kosong = bulanTampil.atDay(1).getDayOfWeek().getValue() - 1;
		for (int i = 0; i < kosong; i++) {
			panelHari.add(new JLabel());
		}
		for (int tanggal = 1; tanggal <= bulanTampil.lengthOfMonth(); tanggal++) {
			LocalDate hari = bulanTampil.atDay(tanggal);
			JButton pilihan = new JButton(String.valueOf(tanggal));
			pilihan.setMargin(new Insets(0, 0, 0, 0));
			pilihan.setPreferredSize(new Dimension(34, 26));
			pilihan.setFocusable(false);
			if (hari.equals(nilai)) {
				pilihan.setBackground(new Color(0xBBDEFB));
				pilihan.setOpaque(true);
			}
			pilihan.addActionListener(e -> {
				nilai = hari;
				tampilan.setText(FORMAT.format(nilai));
				popup.setVisible(false);
			});
			panelHari.add(pilihan);
		}
		panelHari.revalidate();
		panelHari.repaint();
		popup.pack();
	}

}
