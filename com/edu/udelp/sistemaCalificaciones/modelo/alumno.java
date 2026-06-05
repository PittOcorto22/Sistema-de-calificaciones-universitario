package com.edu.udelp.sistemaCalificaciones.modelo;

public class alumno extends usuario {
    private String matricula;
    private int idCarrera;

    public alumno(int id, String nombre, String correo, String contrasena, String matricula, int idCarrera) {
        super(id, nombre, correo, contrasena, "alumno");
        this.matricula = matricula;
        this.idCarrera = idCarrera;
    }
}