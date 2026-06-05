package com.edu.udelp.sistemaCalificaciones.controller;

import com.edu.udelp.sistemaCalificaciones.Main;
import com.edu.udelp.sistemaCalificaciones.db.conexion;
import com.edu.udelp.sistemaCalificaciones.modelo.usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dashboardController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private TableView<usuario> tablaUsuarios;

    @FXML
    public void initialize() {
        cargarDatos();
    }

    public void setUsuario(String nombre) {
        lblBienvenida.setText("Bienvenido al sistema, " + nombre);
    }

    @FXML
    protected void cargarDatos() {
        ObservableList<usuario> listaUsuarios = FXCollections.observableArrayList();

        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "SELECT id, nombre, correo, tipo FROM usuario";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                listaUsuarios.add(new usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("tipo")
                ));
            }

            tablaUsuarios.setItems(listaUsuarios);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void cerrarSesion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 400);

            Stage stageActual = (Stage) lblBienvenida.getScene().getWindow();
            stageActual.close();

            Stage loginStage = new Stage();
            loginStage.setTitle("Sistema de Calificaciones");
            loginStage.setScene(scene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}