package com.edu.udelp.sistemaCalificaciones.modelo;


public class calificacion {
    private int id;
    private int idAlumno;
    private int idGrupo;
    private double parciales;
    private double actividades;
    private double promedioFinal;

    public calificacion(int id, int idAlumno, int idGrupo, double parciales, double actividades) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idGrupo = idGrupo;
        this.parciales = parciales;
        this.actividades = actividades;
        this.promedioFinal = calcularPromedio();
    }

    private double calcularPromedio() {
        return (parciales + actividades) / 2.0;
    }

    public double getPromedioFinal() { return promedioFinal; }
}