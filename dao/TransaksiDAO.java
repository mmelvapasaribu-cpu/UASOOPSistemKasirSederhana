/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
 
import Config.Koneksi;
import model.Transaksi;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Data Access Object untuk header Transaksi (tabel `transaksi`).
 * Detail item transaksi ditangani terpisah oleh {@link DetailTransaksiDAO}.
 *
 * @author FADHIL
 */
public class TransaksiDAO {
 
    /** Menyimpan header transaksi dan mengembalikan ID yang di-generate database. */
    public int simpan(Transaksi transaksi) throws SQLException {
        String sql = "INSERT INTO transaksi (no_transaksi, total_belanja, jumlah_bayar, kembalian) VALUES (?, ?, ?, ?)";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transaksi.getNoTransaksi());
            stmt.setDouble(2, transaksi.getTotalBelanja());
            stmt.setDouble(3, transaksi.getJumlahBayar());
            stmt.setDouble(4, transaksi.getKembalian());
            stmt.executeUpdate();
 
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                throw new SQLException("ID transaksi baru tidak diperoleh dari database.");
            }
        }
    }
 
    public List<Transaksi> semuaTransaksi() throws SQLException {
        String sql = "SELECT * FROM transaksi ORDER BY tanggal_transaksi DESC";
        return jalankanQueryDaftarTransaksi(sql, null, null);
    }
 
    /** Mengambil daftar transaksi dalam rentang tanggal tertentu (dipakai layar Laporan). */
    public List<Transaksi> cariBerdasarkanTanggal(Date tanggalMulai, Date tanggalAkhir) throws SQLException {
        String sql = "SELECT * FROM transaksi WHERE DATE(tanggal_transaksi) BETWEEN ? AND ? ORDER BY tanggal_transaksi DESC";
        return jalankanQueryDaftarTransaksi(sql, tanggalMulai, tanggalAkhir);
    }
 
    public Transaksi cariBerdasarkanId(int idTransaksi) throws SQLException {
        String sql = "SELECT * FROM transaksi WHERE id_transaksi=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, idTransaksi);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapToTransaksi(rs) : null;
            }
        }
    }
 
    private List<Transaksi> jalankanQueryDaftarTransaksi(String sql, Date mulai, Date akhir) throws SQLException {
        List<Transaksi> hasil = new ArrayList<>();
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            if (mulai != null && akhir != null) {
                stmt.setDate(1, mulai);
                stmt.setDate(2, akhir);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hasil.add(mapToTransaksi(rs));
                }
            }
        }
        return hasil;
    }
 
    private Transaksi mapToTransaksi(ResultSet rs) throws SQLException {
        Transaksi transaksi = new Transaksi();
        transaksi.setIdTransaksi(rs.getInt("id_transaksi"));
        transaksi.setNoTransaksi(rs.getString("no_transaksi"));
        transaksi.setTanggalTransaksi(rs.getTimestamp("tanggal_transaksi"));
        transaksi.setTotalBelanja(rs.getDouble("total_belanja"));
        transaksi.setJumlahBayar(rs.getDouble("jumlah_bayar"));
        transaksi.setKembalian(rs.getDouble("kembalian"));
        return transaksi;
    }
}
