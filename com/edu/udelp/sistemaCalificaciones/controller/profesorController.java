package com.edu.udelp.sistemaCalificaciones.controller;

import com.edu.udelp.sistemaCalificaciones.Main;
import com.edu.udelp.sistemaCalificaciones.db.conexion;
import com.edu.udelp.sistemaCalificaciones.modelo.calificacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class profesorController {

    @FXML private Label lblBienvenida;
    @FXML private TableView<calificacion> tablaCalificaciones;
    @FXML private ComboBox<String> cmbAlumno;
    @FXML private ComboBox<String> cmbGrupo;
    @FXML private TextField txtParciales;
    @FXML private TextField txtActividades;
    @FXML private Label lblMensajeForm;

    private int idEdicion = 0;

    @FXML
    public void initialize() {
        cargarListas();
        cargarTabla();
    }

    public void setUsuario(String nombre) {
        lblBienvenida.setText("Profesor en sesion: " + nombre);
    }

    private void cargarListas() {
        ObservableList<String> alumnos = FXCollections.observableArrayList();
        ObservableList<String> grupos = FXCollections.observableArrayList();
        try {
            Connection con = conexion.getInstancia().getConnection();

            String sqlAlumnos = "SELECT u.id, u.nombre FROM usuario u INNER JOIN alumno a ON u.id = a.id";
            Statement stAlumnos = con.createStatement();
            ResultSet rsAlumnos = stAlumnos.executeQuery(sqlAlumnos);
            while (rsAlumnos.next()) {
                alumnos.add(rsAlumnos.getInt("id") + " - " + rsAlumnos.getString("nombre"));
            }
            cmbAlumno.setItems(alumnos);

            String sqlGrupos = "SELECT g.id, m.nombre FROM grupo g INNER JOIN materia m ON g.idMateria = m.id";
            Statement stGrupos = con.createStatement();
            ResultSet rsGrupos = stGrupos.executeQuery(sqlGrupos);
            while (rsGrupos.next()) {
                grupos.add(rsGrupos.getInt("id") + " - " + rsGrupos.getString("nombre"));
            }
            cmbGrupo.setItems(grupos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarTabla() {
        ObservableList<calificacion> listaCalificaciones = FXCollections.observableArrayList();
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "SELECT c.id, u.nombre AS nombreAlumno, m.nombre AS nombreMateria, c.parciales, c.actividades " +
                    "FROM calificacion c " +
                    "INNER JOIN usuario u ON c.idAlumno = u.id " +
                    "INNER JOIN grupo g ON c.idGrupo = g.id " +
                    "INNER JOIN materia m ON g.idMateria = m.id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void seleccionarCalificacion() {
        calificacion seleccionada = tablaCalificaciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            idEdicion = seleccionada.getId();
            txtParciales.setText(String.valueOf(seleccionada.getParciales()));
            txtActividades.setText(String.valueOf(seleccionada.getActividades()));
            lblMensajeForm.setStyle("-fx-text-fill: blue;");
            lblMensajeForm.setText("Editando calificacion ID: " + idEdicion);
        }
    }

    @FXML
    protected void guardarCalificacion() {
        String alumnoSeleccionado = cmbAlumno.getValue();
        String grupoSeleccionado = cmbGrupo.getValue();
        String parcialesTexto = txtParciales.getText();
        String actividadesTexto = txtActividades.getText();

        if (alumnoSeleccionado == null || grupoSeleccionado == null || parcialesTexto.isEmpty() || actividadesTexto.isEmpty()) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Llene todos los campos para nueva");
            return;
        }

        try {
            int idAlumno = Integer.parseInt(alumnoSeleccionado.split(" - ")[0]);
            int idGrupo = Integer.parseInt(grupoSeleccionado.split(" - ")[0]);
            double parciales = Double.parseDouble(parcialesTexto);
            double actividades = Double.parseDouble(actividadesTexto);
            double promedioFinal = (parciales + actividades) / 2.0;

            Connection con = conexion.getInstancia().getConnection();
            String sql = "INSERT INTO calificacion (idAlumno, idGrupo, parciales, actividades, promedioFinal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idGrupo);
            ps.setDouble(3, parciales);
            ps.setDouble(4, actividades);
            ps.setDouble(5, promedioFinal);
            ps.executeUpdate();

            lblMensajeForm.setStyle("-fx-text-fill: green;");
            lblMensajeForm.setText("Calificacion guardada");
            limpiarFormulario();
            cargarTabla();

        } catch (Exception e) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Error al guardar");
        }
    }

    @FXML
    protected void actualizarCalificacion() {
        if (idEdicion == 0) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Seleccione de la tabla para editar");
            return;
        }

        String parcialesTexto = txtParciales.getText();
        String actividadesTexto = txtActividades.getText();

        if (parcialesTexto.isEmpty() || actividadesTexto.isEmpty()) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Llene parciales y actividades");
            return;
        }

        try {
            double parciales = Double.parseDouble(parcialesTexto);
            double actividades = Double.parseDouble(actividadesTexto);
            double promedioFinal = (parciales + actividades) / 2.0;

            Connection con = conexion.getInstancia().getConnection();
            String sql = "UPDATE calificacion SET parciales = ?, actividades = ?, promedioFinal = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, parciales);
            ps.setDouble(2, actividades);
            ps.setDouble(3, promedioFinal);
            ps.setInt(4, idEdicion);
            ps.executeUpdate();

            lblMensajeForm.setStyle("-fx-text-fill: green;");
            lblMensajeForm.setText("Calificacion actualizada");
            limpiarFormulario();
            cargarTabla();

        } catch (Exception e) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Error al actualizar");
        }
    }

    private void limpiarFormulario() {
        cmbAlumno.setValue(null);
        cmbGrupo.setValue(null);
        txtParciales.clear();
        txtActividades.clear();
        idEdicion = 0;
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