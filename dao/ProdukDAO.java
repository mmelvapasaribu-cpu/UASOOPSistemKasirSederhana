/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
 
import Config.Koneksi;
import model.Produk;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Data Access Object untuk entity Produk. Seluruh interaksi JDBC ke tabel
 * `produk` terpusat di kelas ini, memakai PreparedStatement (mencegah SQL
 * Injection) dan try-with-resources (memastikan Connection/Statement/ResultSet
 * selalu tertutup meskipun terjadi exception).
 *
 * @author FADHIL
 */
public class ProdukDAO {
 
    public void simpan(Produk produk) throws SQLException {
        String sql = "INSERT INTO produk (kode_produk, nama_produk, harga, stok, kategori) VALUES (?, ?, ?, ?, ?)";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            isiParameterProduk(stmt, produk);
            stmt.executeUpdate();
        }
    }
 
    public void ubah(Produk produk) throws SQLException {
        String sql = "UPDATE produk SET kode_produk=?, nama_produk=?, harga=?, stok=?, kategori=? WHERE id_produk=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            isiParameterProduk(stmt, produk);
            stmt.setInt(6, produk.getIdProduk());
            stmt.executeUpdate();
        }
    }
 
    public void hapus(int idProduk) throws SQLException {
        String sql = "DELETE FROM produk WHERE id_produk=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, idProduk);
            stmt.executeUpdate();
        }
    }
 
    public Produk cariBerdasarkanId(int idProduk) throws SQLException {
        String sql = "SELECT * FROM produk WHERE id_produk=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, idProduk);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapToProduk(rs) : null;
            }
        }
    }
 
    public Produk cariBerdasarkanKode(String kodeProduk) throws SQLException {
        String sql = "SELECT * FROM produk WHERE kode_produk=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setString(1, kodeProduk);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapToProduk(rs) : null;
            }
        }
    }
 
    public List<Produk> cariBerdasarkanNama(String kataKunci) throws SQLException {
        String sql = "SELECT * FROM produk WHERE nama_produk LIKE ? ORDER BY nama_produk";
        List<Produk> hasil = new ArrayList<>();
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setString(1, "%" + kataKunci + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hasil.add(mapToProduk(rs));
                }
            }
        }
        return hasil;
    }
 
    public List<Produk> semuaProduk() throws SQLException {
        String sql = "SELECT * FROM produk ORDER BY nama_produk";
        List<Produk> hasil = new ArrayList<>();
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                hasil.add(mapToProduk(rs));
            }
        }
        return hasil;
    }
 
    public void kurangiStok(int idProduk, int jumlah) throws SQLException {
        String sql = "UPDATE produk SET stok = stok - ? WHERE id_produk=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, jumlah);
            stmt.setInt(2, idProduk);
            stmt.executeUpdate();
        }
    }
 
    /** Helper privat: mengisi parameter PreparedStatement dari objek Produk (menghindari duplikasi kode). */
    private void isiParameterProduk(PreparedStatement stmt, Produk produk) throws SQLException {
        stmt.setString(1, produk.getKodeProduk());
        stmt.setString(2, produk.getNamaProduk());
        stmt.setDouble(3, produk.getHarga());
        stmt.setInt(4, produk.getStok());
        stmt.setString(5, produk.getKategori());
    }
 
    /** Helper privat: memetakan satu baris ResultSet menjadi objek Produk. */
    private Produk mapToProduk(ResultSet rs) throws SQLException {
        return new Produk(
                rs.getInt("id_produk"),
                rs.getString("kode_produk"),
                rs.getString("nama_produk"),
                rs.getDouble("harga"),
                rs.getInt("stok"),
                rs.getString("kategori")
        );
    }
}
