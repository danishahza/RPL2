package com.fleetflow.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
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
 * Tabel data sederhana dengan kolom pencarian.
 */
public class TabelPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JTextField cari = new JTextField();
	private final TableRowSorter<DefaultTableModel> sorter;

	public TabelPanel(String judul, String[] kolom, List<String[]> baris) {
		super(new BorderLayout(0, 8));
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		DefaultTableModel model = new DefaultTableModel(kolom, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int baris, int kolomTabel) {
				return false;
			}
		};
		for (String[] isi : baris) {
			model.addRow(isi);
		}

		JTable tabel = new JTable(model);
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
