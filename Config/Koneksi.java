/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * Menyediakan koneksi baru ke database MySQL untuk setiap pemanggilan.
 *
 * PENTING: Ubah HOST, PORT, NAMA_DATABASE, USERNAME, dan PASSWORD di bawah
 * ini sesuai konfigurasi MySQL pada komputer Anda (contoh: XAMPP/Laragon
 * biasanya USERNAME="root" dan PASSWORD="").
 *
 * @author FADHIL
 */
public final class Koneksi {
 
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String NAMA_DATABASE = "kasirsederhana";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "fadhil3078";
 
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + NAMA_DATABASE
            + "?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";
 
    private Koneksi() {
        // Mencegah instansiasi; kelas ini hanya menyediakan method statis.
    }
 
    /**
     * Membuka koneksi baru ke database. Pemanggil (DAO) bertanggung jawab
     * menutup koneksi ini, idealnya menggunakan try-with-resources.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "Driver JDBC MySQL (mysql-connector-j) tidak ditemukan di classpath. "
                            + "Pastikan file jar driver sudah ditambahkan ke Libraries project / folder lib/.", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
