/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 
/**
 * Entity yang merepresentasikan data Produk.
 * Kelas ini murni sebagai data holder (POJO) dengan enkapsulasi penuh
 * (field private, akses hanya lewat getter/setter) sesuai prinsip OOP.
 *
 * @author FADHIL
 */
public class Produk {
 
    private int idProduk;
    private String kodeProduk;
    private String namaProduk;
    private double harga;
    private int stok;
    private String kategori;
 
    public Produk() {
    }
 
    /** Konstruktor untuk produk baru yang belum memiliki ID (belum tersimpan di database). */
    public Produk(String kodeProduk, String namaProduk, double harga, int stok, String kategori) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }
 
    /** Konstruktor lengkap, dipakai saat memetakan hasil query dari database. */
    public Produk(int idProduk, String kodeProduk, String namaProduk, double harga, int stok, String kategori) {
        this(kodeProduk, namaProduk, harga, stok, kategori);
        this.idProduk = idProduk;
    }
 
    public int getIdProduk() {
        return idProduk;
    }
 
    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }
 
    public String getKodeProduk() {
        return kodeProduk;
    }
 
    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }
 
    public String getNamaProduk() {
        return namaProduk;
    }
 
    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }
 
    public double getHarga() {
        return harga;
    }
 
    public void setHarga(double harga) {
        this.harga = harga;
    }
 
    public int getStok() {
        return stok;
    }
 
    public void setStok(int stok) {
        this.stok = stok;
    }
 
    public String getKategori() {
        return kategori;
    }
 
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
 
    /** Business rule kecil yang wajar berada di Model: aturan terkait data Produk itu sendiri. */
    public boolean isStokMencukupi(int jumlahDiminta) {
        return this.stok >= jumlahDiminta;
    }
 
    @Override
    public String toString() {
        return namaProduk;
    }
}
