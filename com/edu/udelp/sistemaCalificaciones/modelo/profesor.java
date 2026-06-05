package com.edu.udelp.sistemaCalificaciones.modelo;

public class profesor extends usuario {
    private String numeroEmpleado;

    public profesor(int id, String nombre, String correo, String contrasena, String numeroEmpleado) {
        super(id, nombre, correo, contrasena, "profesor");
        this.numeroEmpleado = numeroEmpleado;
    }
}