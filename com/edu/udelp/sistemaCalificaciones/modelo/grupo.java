package com.edu.udelp.sistemaCalificaciones.modelo;

public class grupo {
    private int id;
    private String nombreMateria;
    private String nombreProfesor;
    private String periodo;

    public grupo(int id, String nombreMateria, String nombreProfesor, String periodo) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.nombreProfesor = nombreProfesor;
        this.periodo = periodo;
    }

    public int getId() { return id; }
    public String getNombreMateria() { return nombreMateria; }
    public String getNombreProfesor() { return nombreProfesor; }
    public String getPeriodo() { return periodo; }
}