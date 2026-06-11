package com.edu.udelp.sistemaCalificaciones.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static conexion instancia;
    private Connection con;

    private final String URL = "jdbc:mysql://localhost:3306/universidad_db";
    private final String USER = "root";
    private final String PASS = "";

    private conexion() {
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static conexion getInstancia() {
        if (instancia == null) {
            instancia = new conexion();
        }
        return instancia;
    }

    public Connection getConnection() {
        return con;
    }

    public void cerrar() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
