package com.edu.udelp.sistemaCalificaciones.modelo;

public class calificacion {
    private int id;
    private String nombreAlumno;
    private String nombreGrupo;
    private double parciales;
    private double actividades;
    private double promedioFinal;
    private String estatus;

    public calificacion(int id, String nombreAlumno, String nombreGrupo, double parciales, double actividades) {
        this.id = id;
        this.nombreAlumno = nombreAlumno;
        this.nombreGrupo = nombreGrupo;
        this.parciales = parciales;
        this.actividades = actividades;
        this.promedioFinal = calcularPromedio();
        this.estatus = calcularEstatus();
    }

    private double calcularPromedio() {
        return (parciales + actividades) / 2.0;
    }

    private String calcularEstatus() {
        if (this.promedioFinal >= 70.0) {
            return "Aprobado";
        } else {
            return "Reprobado";
        }
    }

    public int getId() { return id; }
    public String getNombreAlumno() { return nombreAlumno; }
    public String getNombreGrupo() { return nombreGrupo; }
    public double getParciales() { return parciales; }
    public double getActividades() { return actividades; }
    public double getPromedioFinal() { return promedioFinal; }
    public String getEstatus() { return estatus; }
}