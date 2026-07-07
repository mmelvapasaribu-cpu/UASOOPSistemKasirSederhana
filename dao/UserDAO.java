/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
 
import Config.Koneksi;
import model.User;
 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * Data Access Object untuk entity User (tabel `user`, memakai backtick karena
 * USER adalah kata kunci yang dicadangkan MySQL).
 *
 * Password TIDAK pernah disimpan dalam bentuk teks asli: selalu di-hash
 * dengan SHA-256 terlebih dahulu, baik saat menyimpan maupun saat memverifikasi.
 *
 * @author FADHIL
 */
public class UserDAO {
 
    public User cariBerdasarkanUsername(String username) throws SQLException {
        String sql = "SELECT * FROM `user` WHERE username=?";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new User(
                        rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nama_lengkap")
                );
            }
        }
    }
 
    /** Menyimpan user baru; password di-hash otomatis sebelum disimpan. */
    public void simpan(User user) throws SQLException {
        String sql = "INSERT INTO `user` (username, password, nama_lengkap) VALUES (?, ?, ?)";
        try (Connection koneksi = Koneksi.getConnection();
             PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getNamaLengkap());
            stmt.executeUpdate();
        }
    }
 
    /** Menghasilkan hash SHA-256 dari sebuah password dalam bentuk heksadesimal. */
    public static String hashPassword(String passwordAsli) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hasilHash = digest.digest(passwordAsli.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hasilHash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritma hashing SHA-256 tidak tersedia di JVM ini.", e);
        }
    }
}
