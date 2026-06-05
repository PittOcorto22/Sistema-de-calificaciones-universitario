package com.edu.udelp.sistemaCalificaciones.modelo;

public class materia {
    private int id;
    private String nombre;
    private int idCarrera;
    private int idSemestre;

    public materia(int id, String nombre, int idCarrera, int idSemestre) {
        this.id = id;
        this.nombre = nombre;
        this.idCarrera = idCarrera;
        this.idSemestre = idSemestre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}