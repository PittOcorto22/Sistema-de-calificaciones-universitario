package com.edu.udelp.sistemaCalificaciones.modelo;

public class usuario {
    protected int id;
    protected String nombre;
    protected String correo;
    protected String contrasena;
    protected String tipo;

    public usuario(int id, String nombre, String correo, String contrasena, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }


    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}