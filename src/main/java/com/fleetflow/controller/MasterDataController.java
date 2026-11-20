package com.fleetflow.controller;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.Driver;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.Rute;
import com.fleetflow.domain.StatusDriver;
import com.fleetflow.reports.DeliveryOrderInvoiceReport;
import com.fleetflow.service.MasterDataService;

@Controller
public class MasterDataController {

	private final MasterDataService service;

	private final DeliveryOrderInvoiceReport invoiceReport;

	public MasterDataController(MasterDataService service, DeliveryOrderInvoiceReport invoiceReport) {
		this.service = service;
		this.invoiceReport = invoiceReport;
	}

	public List<Kota> kota() {
		return service.kota();
	}

	public List<Driver> driver() {
		return service.driver();
	}

	public List<Pelanggan> pelanggan() {
		return service.pelanggan();
	}

	public List<Produk> produk() {
		return service.produk();
	}

	public List<Rute> rute() {
		return service.rute();
	}

	public String namaKota(String id) {
		return service.namaKota(id);
	}

	public String namaPelanggan(String id) {
		return service.namaPelanggan(id);
	}

	public String namaProduk(String id) {
		return service.namaProduk(id);
	}

	public String namaDriver(String id) {
		return service.namaDriver(id);
	}

	public String alamatPelanggan(String id) {
		return service.alamatPelanggan(id);
	}

	public void tambahKota(String nama) {
		service.tambahKota(nama);
	}

	public void ubahKota(String id, String nama) {
		service.ubahKota(id, nama);
	}

	public void hapusKota(String id) {
		service.hapusKota(id);
	}

	public void tambahDriver(String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		service.tambahDriver(nama, noTelepon, nomorFleet, kapasitasTon, status, kotaId);
	}

	public void ubahDriver(String id, String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		service.ubahDriver(id, nama, noTelepon, nomorFleet, kapasitasTon, status, kotaId);
	}

	public void hapusDriver(String id) {
		service.hapusDriver(id);
	}

	public void tambahPelanggan(String nama, String alamat, String kotaId) {
		service.tambahPelanggan(nama, alamat, kotaId);
	}

	public void ubahPelanggan(String id, String nama, String alamat, String kotaId) {
		service.ubahPelanggan(id, nama, alamat, kotaId);
	}

	public void hapusPelanggan(String id) {
		service.hapusPelanggan(id);
	}

	public void tambahProduk(String nama) {
		service.tambahProduk(nama);
	}

	public void ubahProduk(String id, String nama) {
		service.ubahProduk(id, nama);
	}

	public void hapusProduk(String id) {
		service.hapusProduk(id);
	}

	public void tambahRute(String kotaAId, String kotaBId, int jarakKm) {
		service.tambahRute(kotaAId, kotaBId, jarakKm);
	}

	public void ubahRute(String id, String kotaAId, String kotaBId, int jarakKm) {
		service.ubahRute(id, kotaAId, kotaBId, jarakKm);
	}

	public void hapusRute(String id) {
		service.hapusRute(id);
	}

	public List<DeliveryOrder> deliveryOrder() {
		return service.deliveryOrder();
	}

	public List<DeliveryOrder> deliveryOrderAktif() {
		return service.deliveryOrderAktif();
	}

	public void tambahDeliveryOrder(DeliveryOrder deliveryOrder) {
		service.tambahDeliveryOrder(deliveryOrder);
	}

	public void ubahDeliveryOrder(DeliveryOrder deliveryOrder) {
		service.ubahDeliveryOrder(deliveryOrder);
	}

	public void hapusDeliveryOrder(String id) {
		service.hapusDeliveryOrder(id);
	}

	public void tugaskanDriver(String deliveryOrderId, String driverId) {
		service.tugaskanDriver(deliveryOrderId, driverId);
	}

	public void mulaiPerjalanan(String deliveryOrderId) {
		service.mulaiPerjalanan(deliveryOrderId);
	}

	public void selesaikanPengiriman(String deliveryOrderId) {
		service.selesaikanPengiriman(deliveryOrderId);
	}

	public void selesaikanDeliveryOrder(String deliveryOrderId) {
		service.selesaikanDeliveryOrder(deliveryOrderId);
	}

	/** Mencetak invoice DO ke berkas PDF melalui template JasperReports. */
	public void cetakInvoiceDeliveryOrder(DeliveryOrder deliveryOrder, File tujuan) {
		invoiceReport.simpanPdf(deliveryOrder, tujuan);
	}
}
