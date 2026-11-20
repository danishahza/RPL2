package com.fleetflow.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Tabel data dengan kolom pencarian dan tombol aksi opsional.
 */
public class TabelPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JTextField cari = new JTextField();
	private final TableRowSorter<DefaultTableModel> sorter;
	private final DefaultTableModel model;
	private final JTable tabel;
	private final Supplier<List<String[]>> sumber;
	private final JPanel panelAksi = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));

	public TabelPanel(String judul, String[] kolom, Supplier<List<String[]>> sumber) {
		super(new BorderLayout(0, 8));
		this.sumber = sumber;
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		model = new DefaultTableModel(kolom, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int baris, int kolomTabel) {
				return false;
			}
		};

		tabel = new JTable(model);
		tabel.setRowHeight(22);
		tabel.setFillsViewportHeight(true);
		tabel.getTableHeader().setReorderingAllowed(false);
		sorter = new TableRowSorter<>(model);
		tabel.setRowSorter(sorter);

		cari.setPreferredSize(new Dimension(200, 24));
		cari.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				saring();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				saring();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				saring();
			}
		});

		JLabel label = new JLabel(judul);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));

		JPanel kolomCari = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
		kolomCari.add(new JLabel("Cari:"));
		kolomCari.add(cari);

		JPanel atas = new JPanel(new BorderLayout());
		atas.add(label, BorderLayout.WEST);
		atas.add(kolomCari, BorderLayout.EAST);

		add(atas, BorderLayout.NORTH);
		add(new JScrollPane(tabel), BorderLayout.CENTER);
		add(panelAksi, BorderLayout.SOUTH);

		refresh();
	}

	public TabelPanel(String judul, String[] kolom, List<String[]> baris) {
		this(judul, kolom, () -> baris);
	}

	public void tambahTombol(JButton tombol) {
		panelAksi.add(tombol);
	}

	/**
	 * Menjalankan aksi ketika sebuah baris diklik dua kali.
	 */
	public void saatBarisDiklik(Runnable aksi) {
		tabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && tabel.getSelectedRow() >= 0) {
					aksi.run();
				}
			}
		});
	}

	/**
	 * Memuat ulang isi tabel dari sumber data.
	 */
	public void refresh() {
		model.setRowCount(0);
		for (String[] baris : sumber.get()) {
			model.addRow(baris);
		}
	}

	/**
	 * ID pada kolom pertama dari baris yang dipilih, atau {@code null} jika belum ada.
	 */
	public String idTerpilih() {
		int barisTampilan = tabel.getSelectedRow();
		if (barisTampilan < 0) {
			return null;
		}
		int barisModel = tabel.convertRowIndexToModel(barisTampilan);
		return (String) model.getValueAt(barisModel, 0);
	}

	private void saring() {
		String kunci = cari.getText().trim();
		if (kunci.isEmpty()) {
			sorter.setRowFilter(null);
			return;
		}
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(kunci)));
	}

}
