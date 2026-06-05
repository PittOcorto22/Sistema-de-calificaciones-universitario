package com.edu.udelp.sistemaCalificaciones;

import com.edu.udelp.sistemaCalificaciones.db.conexion;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection con = conexion.getInstancia().getConnection();

        if (con != null) {
            System.out.println("Conexion exitosa a la base de datos");
            conexion.getInstancia().cerrar();
        } else {
            System.out.println("Fallo la conexion");
        }
    }
}