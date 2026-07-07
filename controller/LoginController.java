/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
 
import dao.UserDAO;
import model.User;
import util.Validator;
 
import java.sql.SQLException;
 
/**
 * Menjembatani View Login dan UserDAO. Berisi seluruh logika autentikasi:
 * validasi input, pencarian user, dan pencocokan hash password.
 *
 * @author FADHIL
 */
public class LoginController {
 
    private final UserDAO userDAO;
 
    public LoginController() {
        this.userDAO = new UserDAO();
    }
 
    /**
     * Memvalidasi kredensial login.
     *
     * @return User yang berhasil login
     * @throws IllegalArgumentException jika username/password kosong, salah, atau tidak ditemukan
     * @throws SQLException jika terjadi kegagalan akses database
     */
    public User login(String username, String password) throws SQLException {
        Validator.validasiTidakKosong(username, "Username");
        Validator.validasiTidakKosong(password, "Password");
 
        User user = userDAO.cariBerdasarkanUsername(username.trim());
        if (user == null) {
            throw new IllegalArgumentException("Username tidak ditemukan.");
        }
 
        String hashInput = UserDAO.hashPassword(password);
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Password salah.");
        }
 
        return user;
    }
}
