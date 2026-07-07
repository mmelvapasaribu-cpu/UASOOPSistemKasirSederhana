/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author FADHIL
 */
public class User {
 
    private int idUser;
    private String username;
    private String password;
    private String namaLengkap;
 
    public User() {
    }
 
    public User(String username, String password, String namaLengkap) {
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
    }
 
    public User(int idUser, String username, String password, String namaLengkap) {
        this(username, password, namaLengkap);
        this.idUser = idUser;
    }
 
    public int getIdUser() {
        return idUser;
    }
 
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getNamaLengkap() {
        return namaLengkap;
    }
 
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
