/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author FADHIL
 */
public class DetailTransaksi {
 
    private final int idProduk;
    private final String namaProduk;
    private final double hargaSatuan;
    private int jumlah;
 
    public DetailTransaksi(int idProduk, String namaProduk, double hargaSatuan, int jumlah) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
    }
 
    public int getIdProduk() {
        return idProduk;
    }
 
    public String getNamaProduk() {
        return namaProduk;
    }
 
    public double getHargaSatuan() {
        return hargaSatuan;
    }
 
    public int getJumlah() {
        return jumlah;
    }
 
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
 
    public double getSubtotal() {
        return hargaSatuan * jumlah;
    }
}
