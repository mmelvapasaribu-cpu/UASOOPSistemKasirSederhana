/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
/**
 * Entity Transaksi. Berperan sebagai "header" yang menampung daftar
 * DetailTransaksi (item-item yang dibeli pada satu transaksi).
 *
 * @author FADHIL
 */
public class Transaksi {
 
    private int idTransaksi;
    private String noTransaksi;
    private Timestamp tanggalTransaksi;
    private double totalBelanja;
    private double jumlahBayar;
    private double kembalian;
    private final List<DetailTransaksi> daftarDetail = new ArrayList<>();
 
    public Transaksi() {
    }
 
    /** Konstruktor untuk transaksi baru yang akan diproses (checkout). */
    public Transaksi(String noTransaksi, double totalBelanja, double jumlahBayar) {
        this.noTransaksi = noTransaksi;
        this.totalBelanja = totalBelanja;
        this.jumlahBayar = jumlahBayar;
        this.kembalian = jumlahBayar - totalBelanja;
    }
 
    public int getIdTransaksi() {
        return idTransaksi;
    }
 
    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }
 
    public String getNoTransaksi() {
        return noTransaksi;
    }
 
    public void setNoTransaksi(String noTransaksi) {
        this.noTransaksi = noTransaksi;
    }
 
    public Timestamp getTanggalTransaksi() {
        return tanggalTransaksi;
    }
 
    public void setTanggalTransaksi(Timestamp tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }
 
    public double getTotalBelanja() {
        return totalBelanja;
    }
 
    public void setTotalBelanja(double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }
 
    public double getJumlahBayar() {
        return jumlahBayar;
    }
 
    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }
 
    public double getKembalian() {
        return kembalian;
    }
 
    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }
 
    /** Mengembalikan salinan tak-termodifikasi agar daftar detail tidak bisa diubah dari luar sembarangan. */
    public List<DetailTransaksi> getDaftarDetail() {
        return Collections.unmodifiableList(daftarDetail);
    }
 
    public void tambahDetail(DetailTransaksi detail) {
        daftarDetail.add(detail);
    }
}
