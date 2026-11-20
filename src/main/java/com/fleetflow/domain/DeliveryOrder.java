package com.fleetflow.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "delivery_order")
public class DeliveryOrder {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "nomor_do", nullable = false, unique = true, length = 20)
	private String nomorDo;

	@Column(name = "kota_asal_id", nullable = false, length = 10)
	private String kotaAsalId;

	@Column(name = "pengirim_id", nullable = false, length = 10)
	private String pengirimId;

	@Column(name = "kota_tujuan_id", nullable = false, length = 10)
	private String kotaTujuanId;

	@Column(name = "penerima_id", nullable = false, length = 10)
	private String penerimaId;

	@Column(name = "tanggal_do", nullable = false)
	private LocalDate tanggalDo;

	@Column(name = "tanggal_pickup", nullable = false)
	private LocalDate tanggalPickup;

	@Column(name = "tanggal_tenggat", nullable = false)
	private LocalDate tanggalTenggat;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20, columnDefinition = "varchar(20) default 'CREATED'")
	private StatusDeliveryOrder status;

	@Column(name = "driver_id", length = 10)
	private String driverId;

	@Transient
	private List<DeliveryOrderItem> items = List.of();

	protected DeliveryOrder() {
	}

	public DeliveryOrder(String id, String nomorDo, String kotaAsalId, String pengirimId, String kotaTujuanId,
			String penerimaId, LocalDate tanggalDo, LocalDate tanggalPickup, LocalDate tanggalTenggat,
			StatusDeliveryOrder status, String driverId) {
		this.id = id;
		this.nomorDo = nomorDo;
		this.kotaAsalId = kotaAsalId;
		this.pengirimId = pengirimId;
		this.kotaTujuanId = kotaTujuanId;
		this.penerimaId = penerimaId;
		this.tanggalDo = tanggalDo;
		this.tanggalPickup = tanggalPickup;
		this.tanggalTenggat = tanggalTenggat;
		this.status = status;
		this.driverId = driverId;
	}

	public String id() {
		return id;
	}

	public String nomorDo() {
		return nomorDo;
	}

	public String kotaAsalId() {
		return kotaAsalId;
	}

	public String pengirimId() {
		return pengirimId;
	}

	public String kotaTujuanId() {
		return kotaTujuanId;
	}

	public String penerimaId() {
		return penerimaId;
	}

	public LocalDate tanggalDo() {
		return tanggalDo;
	}

	public LocalDate tanggalPickup() {
		return tanggalPickup;
	}

	public LocalDate tanggalTenggat() {
		return tanggalTenggat;
	}

	public StatusDeliveryOrder status() {
		return status;
	}

	public String driverId() {
		return driverId;
	}

	public List<DeliveryOrderItem> items() {
		return items;
	}

	public void tetapkanItems(List<DeliveryOrderItem> items) {
		this.items = items == null ? List.of() : List.copyOf(items);
	}

	public double totalBeratTon() {
		return items.stream().mapToDouble(DeliveryOrderItem::beratTon).sum();
	}

	public DeliveryOrder denganPenugasan(String driverIdBaru, StatusDeliveryOrder statusBaru) {
		DeliveryOrder hasil = new DeliveryOrder(id, nomorDo, kotaAsalId, pengirimId, kotaTujuanId, penerimaId,
				tanggalDo, tanggalPickup, tanggalTenggat, statusBaru, driverIdBaru);
		hasil.tetapkanItems(items);
		return hasil;
	}

	public DeliveryOrder denganData(String kotaAsalIdBaru, String pengirimIdBaru, String kotaTujuanIdBaru,
			String penerimaIdBaru, LocalDate tanggalDoBaru, LocalDate tanggalPickupBaru,
			LocalDate tanggalTenggatBaru) {
		DeliveryOrder hasil = new DeliveryOrder(id, nomorDo, kotaAsalIdBaru, pengirimIdBaru, kotaTujuanIdBaru,
				penerimaIdBaru, tanggalDoBaru, tanggalPickupBaru, tanggalTenggatBaru, status, driverId);
		hasil.tetapkanItems(items);
		return hasil;
	}

	@Override
	public boolean equals(Object lain) {
		if (this == lain) {
			return true;
		}
		if (!(lain instanceof DeliveryOrder deliveryOrder)) {
			return false;
		}
		return id != null && id.equals(deliveryOrder.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
