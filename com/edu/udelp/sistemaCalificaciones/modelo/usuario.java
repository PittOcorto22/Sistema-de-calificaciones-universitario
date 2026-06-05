package com.edu.udelp.sistemaCalificaciones.modelo;

public class usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String tipo;

    public usuario(int id, String nombre, String correo, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.tipo = tipo;
    }

    public usuario(int id, String nombre, String correo, String contrasena, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getTipo() { return tipo; }
}