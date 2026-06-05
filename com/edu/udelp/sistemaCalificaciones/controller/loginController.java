package com.edu.udelp.sistemaCalificaciones.controller;

import com.edu.udelp.sistemaCalificaciones.db.conexion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Label lblMensaje;

    @FXML
    protected void iniciarSesion() {
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Por favor, llene todos los campos");
            return;
        }

        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblMensaje.setStyle("-fx-text-fill: green;");
                lblMensaje.setText("Bienvenido, " + rs.getString("nombre"));
            } else {
                lblMensaje.setStyle("-fx-text-fill: red;");
                lblMensaje.setText("Credenciales incorrectas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Error de conexion a la base de datos");
        }
    }
}