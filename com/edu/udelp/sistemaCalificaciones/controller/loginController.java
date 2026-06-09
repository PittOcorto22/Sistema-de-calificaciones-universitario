package com.edu.udelp.sistemaCalificaciones.controller;

import com.edu.udelp.sistemaCalificaciones.Main;
import com.edu.udelp.sistemaCalificaciones.db.conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;

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
                String tipo = rs.getString("tipo");
                String nombre = rs.getString("nombre");

                if (tipo.equals("profesor")) {
                    abrirPantalla(nombre, "view/profesorDashboard.fxml", "Módulo de Profesor");
                } else if (tipo.equals("alumno")) {
                    abrirPantalla(nombre, "view/alumnoDashboard.fxml", "Módulo de Alumno");
                } else {
                    abrirPantalla(nombre, "view/dashboard.fxml", "Panel de Administracion");
                }
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

    private void abrirPantalla(String nombreUsuario, String rutaFXML, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(rutaFXML));
            Scene scene = new Scene(fxmlLoader.load(), 850, 550);

            Object controlador = fxmlLoader.getController();
            if (controlador instanceof dashboardController) {
                ((dashboardController) controlador).setUsuario(nombreUsuario);
            } else if (controlador instanceof profesorController) {
                ((profesorController) controlador).setUsuario(nombreUsuario);
            } else if (controlador instanceof alumnoController) {
                ((alumnoController) controlador).setUsuario(nombreUsuario);
            }

            Stage stageActual = (Stage) txtCorreo.getScene().getWindow();
            stageActual.close();

            Stage nuevoStage = new Stage();
            nuevoStage.setTitle(titulo);
            nuevoStage.setScene(scene);
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("Error al abrir el menu");
        }
    }
}