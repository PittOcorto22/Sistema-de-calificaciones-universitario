package com.edu.udelp.sistemaCalificaciones.modelo;

public class semestre {
    private int id;
    private String nombre;

    public semestre(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}