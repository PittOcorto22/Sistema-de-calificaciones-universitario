package com.edu.udelp.sistemaCalificaciones.controller;

import com.edu.udelp.sistemaCalificaciones.Main;
import com.edu.udelp.sistemaCalificaciones.db.conexion;
import com.edu.udelp.sistemaCalificaciones.modelo.calificacion;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class alumnoController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private TableView<calificacion> tablaCalificaciones;

    private String nombreAlumnoActual;

    public void setUsuario(String nombre) {
        this.nombreAlumnoActual = nombre;
        lblBienvenida.setText("Alumno en sesion: " + nombre);
        cargarTabla();
    }

    private void cargarTabla() {
        ObservableList<calificacion> listaCalificaciones = FXCollections.observableArrayList();

        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "SELECT c.id, u.nombre AS nombreAlumno, m.nombre AS nombreMateria, c.parciales, c.actividades " +
                    "FROM calificacion c " +
                    "INNER JOIN usuario u ON c.idAlumno = u.id " +
                    "INNER JOIN grupo g ON c.idGrupo = g.id " +
                    "INNER JOIN materia m ON g.idMateria = m.id " +
                    "WHERE u.nombre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombreAlumnoActual);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaCalificaciones.add(new calificacion(
                        rs.getInt("id"),
                        rs.getString("nombreAlumno"),
                        rs.getString("nombreMateria"),
                        rs.getDouble("parciales"),
                        rs.getDouble("actividades")
                ));
            }

            tablaCalificaciones.setItems(listaCalificaciones);

        } catch (Exception e) {
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