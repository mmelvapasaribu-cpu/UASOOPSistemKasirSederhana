/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
 
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
 
/**
 * Utility class untuk memformat angka menjadi format mata uang Rupiah,
 * misalnya 15000 menjadi "Rp 15.000". Dipisahkan sebagai kelas util
 * agar logika format tidak berulang di berbagai View (prinsip DRY).
 *
 * @author FADHIL
 */
public final class FormatRupiah {
 
    private static final DecimalFormat FORMATTER;
 
    static {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols(new Locale("in", "ID"));
        simbol.setGroupingSeparator('.');
        FORMATTER = new DecimalFormat("#,##0", simbol);
    }
 
    private FormatRupiah() {
    }
 
    public static String format(double nominal) {
        return "Rp " + FORMATTER.format(nominal);
    }
}
