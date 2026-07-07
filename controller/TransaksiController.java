/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
 
import dao.DetailTransaksiDAO;
import dao.ProdukDAO;
import dao.TransaksiDAO;
import model.DetailTransaksi;
import model.Produk;
import model.Transaksi;
import util.Validator;
 
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
/**
 * Mengelola state keranjang belanja (sisi kasir) dan proses transaksi
 * (checkout/pembayaran). Satu instance kelas ini merepresentasikan satu
 * sesi keranjang belanja yang sedang berjalan di layar kasir.
 *
 * Mengoordinasikan tiga DAO: ProdukDAO (cek & kurangi stok), TransaksiDAO
 * (simpan header transaksi), dan DetailTransaksiDAO (simpan tiap item).
 *
 * @author FADHIL
 */
public class TransaksiController {
 
    private final TransaksiDAO transaksiDAO;
    private final DetailTransaksiDAO detailTransaksiDAO;
    private final ProdukDAO produkDAO;
    private final List<DetailTransaksi> keranjang = new ArrayList<>();
 
    public TransaksiController() {
        this.transaksiDAO = new TransaksiDAO();
        this.detailTransaksiDAO = new DetailTransaksiDAO();
        this.produkDAO = new ProdukDAO();
    }
 
    public void tambahKeKeranjang(Produk produk, int jumlah) {
        Validator.validasiLebihBesarDariNol(jumlah, "Jumlah");
 
        int totalDiminta = jumlah + jumlahDiKeranjang(produk.getIdProduk());
        if (!produk.isStokMencukupi(totalDiminta)) {
            throw new IllegalArgumentException("Stok " + produk.getNamaProduk() + " tidak mencukupi.");
        }
 
        for (DetailTransaksi item : keranjang) {
            if (item.getIdProduk() == produk.getIdProduk()) {
                item.setJumlah(item.getJumlah() + jumlah);
                return;
            }
        }
        keranjang.add(new DetailTransaksi(produk.getIdProduk(), produk.getNamaProduk(), produk.getHarga(), jumlah));
    }
 
    public void hapusDariKeranjang(int indeks) {
        keranjang.remove(indeks);
    }
 
    public void kosongkanKeranjang() {
        keranjang.clear();
    }
 
    public List<DetailTransaksi> getKeranjang() {
        return Collections.unmodifiableList(keranjang);
    }
 
    public double getTotalBelanja() {
        double total = 0;
        for (DetailTransaksi item : keranjang) {
            total += item.getSubtotal();
        }
        return total;
    }
 
    /**
     * Memproses pembayaran: menyimpan header transaksi, menyimpan tiap detail
     * item, mengurangi stok produk, lalu mengosongkan keranjang.
     *
     * Catatan: pada aplikasi produksi, ketiga langkah simpan/kurangi-stok ini
     * sebaiknya dibungkus satu transaksi database (commit/rollback) agar
     * atomik. Di sini dibuat sekuensial agar setiap DAO tetap sederhana dan
     * hanya menangani satu tabel.
     */
    public Transaksi prosesPembayaran(double jumlahBayar) throws SQLException {
        if (keranjang.isEmpty()) {
            throw new IllegalArgumentException("Keranjang belanja masih kosong.");
        }
        double total = getTotalBelanja();
        if (jumlahBayar < total) {
            throw new IllegalArgumentException("Jumlah bayar kurang dari total belanja.");
        }
 
        Transaksi transaksi = new Transaksi(buatNomorTransaksi(), total, jumlahBayar);
        int idTransaksiBaru = transaksiDAO.simpan(transaksi);
        transaksi.setIdTransaksi(idTransaksiBaru);
 
        for (DetailTransaksi item : keranjang) {
            detailTransaksiDAO.simpan(idTransaksiBaru, item);
            produkDAO.kurangiStok(item.getIdProduk(), item.getJumlah());
            transaksi.tambahDetail(item);
        }
 
        kosongkanKeranjang();
        return transaksi;
    }
 
    public List<Transaksi> riwayatTransaksi() throws SQLException {
        return transaksiDAO.semuaTransaksi();
    }
 
    public List<Transaksi> riwayatBerdasarkanTanggal(Date tanggalMulai, Date tanggalAkhir) throws SQLException {
        return transaksiDAO.cariBerdasarkanTanggal(tanggalMulai, tanggalAkhir);
    }
 
    public List<DetailTransaksi> detailItemTransaksi(int idTransaksi) throws SQLException {
        return detailTransaksiDAO.cariBerdasarkanIdTransaksi(idTransaksi);
    }
 
    private int jumlahDiKeranjang(int idProduk) {
        for (DetailTransaksi item : keranjang) {
            if (item.getIdProduk() == idProduk) {
                return item.getJumlah();
            }
        }
        return 0;
    }
 
    private String buatNomorTransaksi() {
        return "TRX-" + System.currentTimeMillis();
    }
}
