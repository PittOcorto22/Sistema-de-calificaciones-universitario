package com.edu.udelp.sistemaCalificaciones.modelo;

public class carrera {
    private int id;
    private String nombre;

    public carrera(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}