package com.fleetflow.ui.operasional;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fleetflow.controller.MasterDataController;
import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.Driver;
import com.fleetflow.domain.StatusDeliveryOrder;
import com.fleetflow.domain.StatusDriver;
import com.fleetflow.ui.TabelPanel;

/**
 * Halaman dispatch untuk role dispatcher: menugaskan driver ke DO baru,
 * memulai perjalanan, dan menyelesaikan pengiriman.
 *
 * <p>Halaman ini hanya menampilkan DO aktif. Setiap perubahan status DO
 * sekaligus memperbarui status driver yang ditugaskan.
 */
public class DispatchPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final String[] KOLOM = { "No DO", "Asal", "Tujuan", "Pengirim", "Penerima", "Loading", "Deadline",
			"Status", "Driver" };

	private final MasterDataController data;
	private final TabelPanel tabel;

	public DispatchPanel(MasterDataController data) {
		super(new BorderLayout());
		this.data = data;
		this.tabel = new TabelPanel("Penugasan Delivery Order", KOLOM, this::barisTampilan);

		JButton detail = new JButton("Detail");
		detail.addActionListener(e -> tampilkanDetail());
		JButton tugaskan = new JButton("Tugaskan Driver");
		tugaskan.addActionListener(e -> tugaskanDriver());
		JButton mulai = new JButton("Mulai Perjalanan");
		mulai.addActionListener(e -> mulaiPerjalanan());
		JButton selesai = new JButton("Selesaikan Pengiriman");
		selesai.addActionListener(e -> selesaikanPengiriman());

		tabel.tambahTombol(detail);
		tabel.tambahTombol(tugaskan);
		tabel.tambahTombol(mulai);
		tabel.tambahTombol(selesai);
		tabel.saatBarisDiklik(this::tampilkanDetail);

		add(tabel, BorderLayout.CENTER);
	}

	private List<String[]> barisTampilan() {
		return data.deliveryOrderAktif().stream()
				.map(deliveryOrder -> new String[] { deliveryOrder.nomorDo(),
						data.namaKota(deliveryOrder.kotaAsalId()), data.namaKota(deliveryOrder.kotaTujuanId()),
						data.namaPelanggan(deliveryOrder.pengirimId()),
						data.namaPelanggan(deliveryOrder.penerimaId()),
						deliveryOrder.tanggalPickup().toString(), deliveryOrder.tanggalTenggat().toString(),
						deliveryOrder.status().getLabel(),
						deliveryOrder.driverId() == null ? "-" : data.namaDriver(deliveryOrder.driverId()) })
				.toList();
	}

	private void tampilkanDetail() {
		DeliveryOrder deliveryOrder = dataTerpilih();
		if (deliveryOrder != null) {
			DeliveryOrderDetail.tampilkan(this, data, deliveryOrder);
		}
	}

	private void tugaskanDriver() {
		DeliveryOrder deliveryOrder = dataTerpilih();
		if (deliveryOrder == null) {
			return;
		}
		if (deliveryOrder.status() != StatusDeliveryOrder.CREATED) {
			pesanInfo("Driver hanya dapat ditugaskan pada DO berstatus Created.");
			return;
		}
		List<Driver> tersedia = data.driver().stream()
				.filter(driver -> driver.status() == StatusDriver.AVAILABLE)
				.toList();
		if (tersedia.isEmpty()) {
			pesanInfo("Belum ada driver berstatus Available.");
			return;
		}

		JComboBox<String> combo = new JComboBox<>(tersedia.stream()
				.map(driver -> driver.nama() + " (" + driver.nomorFleet() + ")")
				.toArray(String[]::new));
		int pilihan = JOptionPane.showConfirmDialog(this, combo,
				"Tugaskan driver untuk DO " + deliveryOrder.nomorDo(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (pilihan != JOptionPane.OK_OPTION) {
			return;
		}
		Driver driver = tersedia.get(combo.getSelectedIndex());
		jalankan("Gagal menugaskan driver", () -> data.tugaskanDriver(deliveryOrder.id(), driver.id()));
	}

	private void mulaiPerjalanan() {
		DeliveryOrder deliveryOrder = dataTerpilih();
		if (deliveryOrder == null) {
			return;
		}
		if (deliveryOrder.status() != StatusDeliveryOrder.ASSIGNED) {
			pesanInfo("Perjalanan hanya dapat dimulai pada DO berstatus Assigned.");
			return;
		}
		if (!konfirmasi("Mulai perjalanan untuk DO " + deliveryOrder.nomorDo() + "?")) {
			return;
		}
		jalankan("Gagal memulai perjalanan", () -> data.mulaiPerjalanan(deliveryOrder.id()));
	}

	private void selesaikanPengiriman() {
		DeliveryOrder deliveryOrder = dataTerpilih();
		if (deliveryOrder == null) {
			return;
		}
		if (deliveryOrder.status() != StatusDeliveryOrder.IN_TRANSIT) {
			pesanInfo("Pengiriman hanya dapat diselesaikan pada DO berstatus In Transit.");
			return;
		}
		if (!konfirmasi("Selesaikan pengiriman DO " + deliveryOrder.nomorDo() + "?")) {
			return;
		}
		jalankan("Gagal menyelesaikan pengiriman", () -> data.selesaikanPengiriman(deliveryOrder.id()));
	}

	private DeliveryOrder dataTerpilih() {
		String nomor = tabel.idTerpilih();
		if (nomor == null) {
			pesanInfo("Pilih baris delivery order terlebih dahulu.");
			return null;
		}
		return data.deliveryOrderAktif().stream()
				.filter(deliveryOrder -> deliveryOrder.nomorDo().equals(nomor))
				.findFirst()
				.orElse(null);
	}

	private void jalankan(String pesanGagal, Runnable aksi) {
		try {
			aksi.run();
			tabel.refresh();
		}
		catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), pesanGagal, JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean konfirmasi(String pesan) {
		return JOptionPane.showConfirmDialog(this, pesan, "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	private void pesanInfo(String pesan) {
		JOptionPane.showMessageDialog(this, pesan, "Informasi", JOptionPane.INFORMATION_MESSAGE);
	}

}
