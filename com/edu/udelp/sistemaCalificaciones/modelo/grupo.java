package com.edu.udelp.sistemaCalificaciones.modelo;

public class grupo {
    private int id;
    private int idMateria;
    private int idProfesor;
    private String periodo;

    public grupo(int id, int idMateria, int idProfesor, String periodo) {
        this.id = id;
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
        this.periodo = periodo;
    }

    public int getId() { return id; }
    public String getPeriodo() { return periodo; }
}