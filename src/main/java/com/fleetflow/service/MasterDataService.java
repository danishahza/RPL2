package com.fleetflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fleetflow.dao.MasterDataDao;
import com.fleetflow.domain.DeliveryOrder;
import com.fleetflow.domain.Driver;
import com.fleetflow.domain.Kota;
import com.fleetflow.domain.Pelanggan;
import com.fleetflow.domain.Produk;
import com.fleetflow.domain.Rute;
import com.fleetflow.domain.StatusDriver;

@Service
public class MasterDataService {

	private final MasterDataDao dao;

	public MasterDataService(MasterDataDao dao) {
		this.dao = dao;
	}

	public List<Kota> kota() {
		return dao.kota();
	}

	public List<Driver> driver() {
		return dao.driver();
	}

	public List<Pelanggan> pelanggan() {
		return dao.pelanggan();
	}

	public List<Produk> produk() {
		return dao.produk();
	}

	public List<Rute> rute() {
		return dao.rute();
	}

	public String namaKota(String id) {
		return dao.namaKota(id);
	}

	public String namaPelanggan(String id) {
		return dao.namaPelanggan(id);
	}

	public String namaProduk(String id) {
		return dao.namaProduk(id);
	}

	public String namaDriver(String id) {
		return dao.namaDriver(id);
	}

	public String alamatPelanggan(String id) {
		return dao.alamatPelanggan(id);
	}

	public void tambahKota(String nama) {
		dao.tambahKota(nama);
	}

	public void ubahKota(String id, String nama) {
		dao.ubahKota(id, nama);
	}

	public void hapusKota(String id) {
		dao.hapusKota(id);
	}

	public void tambahDriver(String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		dao.tambahDriver(nama, noTelepon, nomorFleet, kapasitasTon, status, kotaId);
	}

	public void ubahDriver(String id, String nama, String noTelepon, String nomorFleet, int kapasitasTon,
			StatusDriver status, String kotaId) {
		dao.ubahDriver(id, nama, noTelepon, nomorFleet, kapasitasTon, status, kotaId);
	}

	public void hapusDriver(String id) {
		dao.hapusDriver(id);
	}

	public void tambahPelanggan(String nama, String alamat, String kotaId) {
		dao.tambahPelanggan(nama, alamat, kotaId);
	}

	public void ubahPelanggan(String id, String nama, String alamat, String kotaId) {
		dao.ubahPelanggan(id, nama, alamat, kotaId);
	}

	public void hapusPelanggan(String id) {
		dao.hapusPelanggan(id);
	}

	public void tambahProduk(String nama) {
		dao.tambahProduk(nama);
	}

	public void ubahProduk(String id, String nama) {
		dao.ubahProduk(id, nama);
	}

	public void hapusProduk(String id) {
		dao.hapusProduk(id);
	}

	public void tambahRute(String kotaAId, String kotaBId, int jarakKm) {
		dao.tambahRute(kotaAId, kotaBId, jarakKm);
	}

	public void ubahRute(String id, String kotaAId, String kotaBId, int jarakKm) {
		dao.ubahRute(id, kotaAId, kotaBId, jarakKm);
	}

	public void hapusRute(String id) {
		dao.hapusRute(id);
	}

	public List<DeliveryOrder> deliveryOrder() {
		return dao.deliveryOrder();
	}

	public List<DeliveryOrder> deliveryOrderAktif() {
		return dao.deliveryOrderAktif();
	}

	public void tambahDeliveryOrder(DeliveryOrder deliveryOrder) {
		dao.tambahDeliveryOrder(deliveryOrder);
	}

	public void ubahDeliveryOrder(DeliveryOrder deliveryOrder) {
		dao.ubahDeliveryOrder(deliveryOrder);
	}

	public void hapusDeliveryOrder(String id) {
		dao.hapusDeliveryOrder(id);
	}

	public void tugaskanDriver(String deliveryOrderId, String driverId) {
		dao.tugaskanDriver(deliveryOrderId, driverId);
	}

	public void mulaiPerjalanan(String deliveryOrderId) {
		dao.mulaiPerjalanan(deliveryOrderId);
	}

	public void selesaikanPengiriman(String deliveryOrderId) {
		dao.selesaikanPengiriman(deliveryOrderId);
	}

	public void selesaikanDeliveryOrder(String deliveryOrderId) {
		dao.selesaikanDeliveryOrder(deliveryOrderId);
	}
}
