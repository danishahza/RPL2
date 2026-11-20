package com.fleetflow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery_order_item")
public class DeliveryOrderItem {

	@Id
	@Column(name = "id", nullable = false, length = 10)
	private String id;

	@Column(name = "delivery_order_id", nullable = false, length = 10)
	private String deliveryOrderId;

	@Column(name = "produk_id", nullable = false, length = 10)
	private String produkId;

	@Column(name = "berat_ton", nullable = false)
	private double beratTon;

	protected DeliveryOrderItem() {
	}

	public DeliveryOrderItem(String id, String deliveryOrderId, String produkId, double beratTon) {
		this.id = id;
		this.deliveryOrderId = deliveryOrderId;
		this.produkId = produkId;
		this.beratTon = beratTon;
	}

	public String id() {
		return id;
	}

	public String deliveryOrderId() {
		return deliveryOrderId;
	}

	public String produkId() {
		return produkId;
	}

	public double beratTon() {
		return beratTon;
	}

	@Override
	public boolean equals(Object lain) {
		if (this == lain) {
			return true;
		}
		if (!(lain instanceof DeliveryOrderItem item)) {
			return false;
		}
		return id != null && id.equals(item.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
