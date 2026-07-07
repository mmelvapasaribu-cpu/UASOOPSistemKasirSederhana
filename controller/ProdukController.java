/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
 
import dao.ProdukDAO;
import model.Produk;
import util.Validator;
 
import java.sql.SQLException;
import java.util.List;
 
/**
 * Menjembatani View dan ProdukDAO untuk seluruh operasi terkait Produk.
 * Seluruh validasi bisnis untuk data produk ditempatkan di sini, sehingga
 * View cukup fokus menampilkan UI dan DAO cukup fokus pada query database
 * (Single Responsibility Principle).
 *
 * @author FADHIL
 */
public class ProdukController {
 
    private final ProdukDAO produkDAO;
 
    public ProdukController() {
        this.produkDAO = new ProdukDAO();
    }
 
    public void tambahProduk(String kode, String nama, double harga, int stok, String kategori) throws SQLException {
        validasiInputProduk(kode, nama, harga, stok);
        if (produkDAO.cariBerdasarkanKode(kode) != null) {
            throw new IllegalArgumentException("Kode produk '" + kode + "' sudah digunakan.");
        }
        produkDAO.simpan(new Produk(kode, nama, harga, stok, kategori));
    }
 
    public void perbaruiProduk(int idProduk, String kode, String nama, double harga, int stok, String kategori)
            throws SQLException {
        validasiInputProduk(kode, nama, harga, stok);
        produkDAO.ubah(new Produk(idProduk, kode, nama, harga, stok, kategori));
    }
 
    public void hapusProduk(int idProduk) throws SQLException {
        produkDAO.hapus(idProduk);
    }
 
    public Produk cariProdukById(int idProduk) throws SQLException {
        return produkDAO.cariBerdasarkanId(idProduk);
    }
 
    public List<Produk> semuaProduk() throws SQLException {
        return produkDAO.semuaProduk();
    }
 
    public List<Produk> cariProduk(String kataKunci) throws SQLException {
        return produkDAO.cariBerdasarkanNama(kataKunci);
    }
 
    private void validasiInputProduk(String kode, String nama, double harga, int stok) {
        Validator.validasiTidakKosong(kode, "Kode produk");
        Validator.validasiTidakKosong(nama, "Nama produk");
        Validator.validasiTidakNegatif(harga, "Harga");
        Validator.validasiTidakNegatif(stok, "Stok");
    }
}
