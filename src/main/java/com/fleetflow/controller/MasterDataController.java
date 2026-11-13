package com.fleetflow.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.Driver;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.Rute;
import com.fleetflow.domain.StatusDriver;
import com.fleetflow.service.MasterDataService;

@Controller
public class MasterDataController {

	private final MasterDataService service;

	public MasterDataController(MasterDataService service) {
		this.service = service;
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

	public void tambahDeliveryOrder(String kotaAsalId, String pengirimId, String kotaTujuanId, String penerimaId,
			LocalDate tanggalDo, LocalDate tanggalPickup, LocalDate tanggalTenggat, String produkId,
			double beratTon) {
		service.tambahDeliveryOrder(kotaAsalId, pengirimId, kotaTujuanId, penerimaId, tanggalDo, tanggalPickup,
				tanggalTenggat, produkId, beratTon);
	}

	public void ubahDeliveryOrder(String id, String kotaAsalId, String pengirimId, String kotaTujuanId,
			String penerimaId, LocalDate tanggalDo, LocalDate tanggalPickup, LocalDate tanggalTenggat,
			String produkId, double beratTon) {
		service.ubahDeliveryOrder(id, kotaAsalId, pengirimId, kotaTujuanId, penerimaId, tanggalDo, tanggalPickup,
				tanggalTenggat, produkId, beratTon);
	}

	public void hapusDeliveryOrder(String id) {
		service.hapusDeliveryOrder(id);
	}
}
