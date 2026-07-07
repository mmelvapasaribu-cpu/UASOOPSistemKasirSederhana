/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
 
/**
 * Kumpulan method validasi input yang dipakai bersama oleh berbagai Controller
 * dan View, agar aturan validasi tidak berulang-ulang di banyak tempat
 * (prinsip DRY / Don't Repeat Yourself).
 *
 * @author FADHIL
 */
public final class Validator {
 
    private Validator() {
        // Mencegah instansiasi; kelas ini hanya menyediakan method statis.
    }
 
    public static void validasiTidakKosong(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
    }
 
    public static void validasiTidakNegatif(double nilai, String namaField) {
        if (nilai < 0) {
            throw new IllegalArgumentException(namaField + " tidak boleh bernilai negatif.");
        }
    }
 
    public static void validasiTidakNegatif(int nilai, String namaField) {
        if (nilai < 0) {
            throw new IllegalArgumentException(namaField + " tidak boleh bernilai negatif.");
        }
    }
 
    public static void validasiLebihBesarDariNol(int nilai, String namaField) {
        if (nilai <= 0) {
            throw new IllegalArgumentException(namaField + " harus lebih besar dari 0.");
        }
    }
 
    /** Mengubah teks menjadi double; melempar pesan error yang ramah jika formatnya salah. */
    public static double parseDouble(String teks, String namaField) {
        try {
            return Double.parseDouble(teks.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(namaField + " harus berupa angka.");
        }
    }
 
    /** Mengubah teks menjadi int; melempar pesan error yang ramah jika formatnya salah. */
    public static int parseInt(String teks, String namaField) {
        try {
            return Integer.parseInt(teks.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(namaField + " harus berupa bilangan bulat.");
        }
    }
}
