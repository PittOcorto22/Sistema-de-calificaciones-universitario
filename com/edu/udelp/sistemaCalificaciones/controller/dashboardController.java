package com.edu.udelp.sistemaCalificaciones.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class dashboardController {

    @FXML
    private Label lblBienvenida;

    public void setUsuario(String nombre) {
        lblBienvenida.setText("Bienvenido al sistema, " + nombre);
    }

    @FXML
    protected void cerrarSesion() {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        stage.close();
    }
}