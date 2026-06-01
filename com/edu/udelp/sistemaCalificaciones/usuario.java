package com.edu.udelp.sistemaCalificaciones;

public class usuario {
    protected int id;
    protected String nombre;
    protected String correo;
    protected String contrasena;
    protected String tipo; // "alumno", "profesor", "admin"

    public usuario(int id, String nombre, String correo, String contrasena, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    // Aquí irían los getters y setters tradicionales...
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}