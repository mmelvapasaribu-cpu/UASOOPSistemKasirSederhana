/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
 
import Config.Koneksi;
import model.DetailTransaksi;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Data Access Object untuk detail item transaksi (tabel `detail_transaksi`).
 * Dipisah dari TransaksiDAO agar masing-masing DAO hanya bertanggung jawab
 * atas satu tabel (Single Responsibility Principle).
 *
 * @author FADHIL
 */
public class DetailTransaksiDAO {
 
    /** Menyimpan satu baris detail item untuk transaksi dengan idTransaksi tertentu. */
    public void simpan(int idTransaksi, DetailTransaksi detail) throws SQLException {
        String sql = "INSERT INTO detail_transaksi "
                + "(id_transaksi, id_produk, nama_produk, harga_satuan, jumlah, subtotal) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, idTransaksi);
            stmt.setInt(2, detail.getIdProduk());
            stmt.setString(3, detail.getNamaProduk());
            stmt.setDouble(4, detail.getHargaSatuan());
            stmt.setInt(5, detail.getJumlah());
            stmt.setDouble(6, detail.getSubtotal());
            stmt.executeUpdate();
        }
    }
 
    /** Mengambil seluruh item detail milik satu transaksi tertentu. */
    public List<DetailTransaksi> cariBerdasarkanIdTransaksi(int idTransaksi) throws SQLException {
        String sql = "SELECT * FROM detail_transaksi WHERE id_transaksi=?";
        List<DetailTransaksi> hasil = new ArrayList<>();
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, idTransaksi);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new DetailTransaksi(
                            rs.getInt("id_produk"),
                            rs.getString("nama_produk"),
                            rs.getDouble("harga_satuan"),
                            rs.getInt("jumlah")
                    ));
                }
            }
        }
        return hasil;
    }
}
