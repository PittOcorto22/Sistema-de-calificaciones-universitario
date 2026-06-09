package com.edu.udelp.sistemaCalificaciones.controller;

import com.edu.udelp.sistemaCalificaciones.Main;
import com.edu.udelp.sistemaCalificaciones.db.conexion;
import com.edu.udelp.sistemaCalificaciones.modelo.usuario;
import com.edu.udelp.sistemaCalificaciones.modelo.materia;
import com.edu.udelp.sistemaCalificaciones.modelo.carrera;
import com.edu.udelp.sistemaCalificaciones.modelo.semestre;
import com.edu.udelp.sistemaCalificaciones.modelo.grupo;
import com.edu.udelp.sistemaCalificaciones.modelo.calificacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dashboardController {

    @FXML private Label lblBienvenida;

    @FXML private TableView<usuario> tablaUsuarios;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private ComboBox<String> cmbCarreraUsuario;
    @FXML private TextField txtDatoExtra;
    @FXML private Label lblMensajeForm;

    @FXML private TableView<materia> tablaMaterias;
    @FXML private TextField txtNombreMateria;
    @FXML private ComboBox<String> cmbCarreraMateria;
    @FXML private ComboBox<String> cmbSemestreMateria;
    @FXML private Label lblMensajeMateria;

    @FXML private TableView<carrera> tablaCarreras;
    @FXML private TextField txtNombreCarrera;
    @FXML private Label lblMensajeCarrera;

    @FXML private TableView<semestre> tablaSemestres;
    @FXML private TextField txtNombreSemestre;
    @FXML private Label lblMensajeSemestre;

    @FXML private TableView<grupo> tablaGrupos;
    @FXML private ComboBox<String> cmbMateriaGrupo;
    @FXML private ComboBox<String> cmbProfesorGrupo;
    @FXML private TextField txtPeriodoGrupo;
    @FXML private Label lblMensajeGrupo;

    @FXML private TableView<calificacion> tablaCalificacionesAdmin;
    @FXML private TextField txtParcialesAdmin;
    @FXML private TextField txtActividadesAdmin;
    @FXML private Label lblMensajeCalificacionesAdmin;

    private int idCalificacionEdicionAdmin = 0;

    @FXML
    public void initialize() {
        cargarDatos();
        cargarCatalogos();
        cargarMaterias();
        cargarListasGrupos();
        cargarGrupos();
        cargarTodasCalificaciones();
    }

    public void setUsuario(String nombre) {
        lblBienvenida.setText("Bienvenido al sistema, " + nombre);
    }

    @FXML
    protected void cargarTodasCalificaciones() {
        ObservableList<calificacion> lista = FXCollections.observableArrayList();
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
                lista.add(new calificacion(
                        rs.getInt("id"),
                        rs.getString("nombreAlumno"),
                        rs.getString("nombreMateria"),
                        rs.getDouble("parciales"),
                        rs.getDouble("actividades")
                ));
            }
            tablaCalificacionesAdmin.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void seleccionarCalificacionAdmin() {
        calificacion seleccionada = tablaCalificacionesAdmin.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            idCalificacionEdicionAdmin = seleccionada.getId();
            txtParcialesAdmin.setText(String.valueOf(seleccionada.getParciales()));
            txtActividadesAdmin.setText(String.valueOf(seleccionada.getActividades()));
            lblMensajeCalificacionesAdmin.setStyle("-fx-text-fill: blue;");
            lblMensajeCalificacionesAdmin.setText("Modificando acta ID: " + idCalificacionEdicionAdmin);
        }
    }

    @FXML
    protected void actualizarCalificacionAdmin() {
        if (idCalificacionEdicionAdmin == 0) {
            lblMensajeCalificacionesAdmin.setStyle("-fx-text-fill: red;");
            lblMensajeCalificacionesAdmin.setText("Seleccione una fila de la lista");
            return;
        }

        String parcialesTexto = txtParcialesAdmin.getText();
        String actividadesTexto = txtActividadesAdmin.getText();

        if (parcialesTexto.isEmpty() || actividadesTexto.isEmpty()) {
            lblMensajeCalificacionesAdmin.setStyle("-fx-text-fill: red;");
            lblMensajeCalificacionesAdmin.setText("Complete ambas calificaciones");
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
            ps.setInt(4, idCalificacionEdicionAdmin);
            ps.executeUpdate();

            lblMensajeCalificacionesAdmin.setStyle("-fx-text-fill: green;");
            lblMensajeCalificacionesAdmin.setText("Acta corregida con éxito");

            txtParcialesAdmin.clear();
            txtActividadesAdmin.clear();
            idCalificacionEdicionAdmin = 0;

            cargarTodasCalificaciones();

        } catch (Exception e) {
            lblMensajeCalificacionesAdmin.setStyle("-fx-text-fill: red;");
            lblMensajeCalificacionesAdmin.setText("Error al actualizar registros");
        }
    }

    private void cargarCatalogos() {
        ObservableList<carrera> listaCarreras = FXCollections.observableArrayList();
        ObservableList<semestre> listaSemestres = FXCollections.observableArrayList();
        ObservableList<String> comboCarreras = FXCollections.observableArrayList();
        ObservableList<String> comboSemestres = FXCollections.observableArrayList();

        try {
            Connection con = conexion.getInstancia().getConnection();

            Statement stCarrera = con.createStatement();
            ResultSet rsCarrera = stCarrera.executeQuery("SELECT * FROM carrera");
            while (rsCarrera.next()) {
                listaCarreras.add(new carrera(rsCarrera.getInt("id"), rsCarrera.getString("nombre")));
                comboCarreras.add(rsCarrera.getInt("id") + " - " + rsCarrera.getString("nombre"));
            }
            tablaCarreras.setItems(listaCarreras);
            cmbCarreraMateria.setItems(comboCarreras);
            cmbCarreraUsuario.setItems(comboCarreras);

            Statement stSemestre = con.createStatement();
            ResultSet rsSemestre = stSemestre.executeQuery("SELECT * FROM semestre");
            while (rsSemestre.next()) {
                listaSemestres.add(new semestre(rsSemestre.getInt("id"), rsSemestre.getString("nombre")));
                comboSemestres.add(rsSemestre.getInt("id") + " - " + rsSemestre.getString("nombre"));
            }
            tablaSemestres.setItems(listaSemestres);
            cmbSemestreMateria.setItems(comboSemestres);

        } catch (SQLException e) {
            System.out.println("Error al cargar catálogos: " + e.getMessage());
        }
    }

    private void cargarListasGrupos() {
        ObservableList<String> comboMaterias = FXCollections.observableArrayList();
        ObservableList<String> comboProfesores = FXCollections.observableArrayList();

        try {
            Connection con = conexion.getInstancia().getConnection();

            Statement stMaterias = con.createStatement();
            ResultSet rsMaterias = stMaterias.executeQuery("SELECT id, nombre FROM materia");
            while (rsMaterias.next()) {
                comboMaterias.add(rsMaterias.getInt("id") + " - " + rsMaterias.getString("nombre"));
            }
            cmbMateriaGrupo.setItems(comboMaterias);

            Statement stProfes = con.createStatement();
            ResultSet rsProfes = stProfes.executeQuery("SELECT id, nombre FROM usuario WHERE tipo = 'profesor'");
            while (rsProfes.next()) {
                comboProfesores.add(rsProfes.getInt("id") + " - " + rsProfes.getString("nombre"));
            }
            cmbProfesorGrupo.setItems(comboProfesores);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void cargarGrupos() {
        ObservableList<grupo> listaGrupos = FXCollections.observableArrayList();
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "SELECT g.id, m.nombre AS materia, u.nombre AS profesor, g.periodo " +
                    "FROM group_table g " +
                    "INNER JOIN materia m ON g.idMateria = m.id " +
                    "INNER JOIN usuario u ON g.idProfesor = u.id";
            // Nota de consistencia: si tu tabla en MySQL se llama "grupo", cámbialo a "FROM grupo g" abajo:
            sql = "SELECT g.id, m.nombre AS materia, u.nombre AS profesor, g.periodo FROM grupo g INNER JOIN materia m ON g.idMateria = m.id INNER JOIN usuario u ON g.idProfesor = u.id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listaGrupos.add(new grupo(
                        rs.getInt("id"), rs.getString("materia"), rs.getString("profesor"), rs.getString("periodo")
                ));
            }
            tablaGrupos.setItems(listaGrupos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void guardarGrupo() {
        String materiaSel = cmbMateriaGrupo.getValue();
        String profeSel = cmbProfesorGrupo.getValue();
        String periodo = txtPeriodoGrupo.getText();

        if (materiaSel == null || profeSel == null || periodo.isEmpty()) {
            lblMensajeGrupo.setStyle("-fx-text-fill: red;");
            lblMensajeGrupo.setText("Llene todos los campos");
            return;
        }

        try {
            int idMateria = Integer.parseInt(materiaSel.split(" - ")[0]);
            int idProfesor = Integer.parseInt(profeSel.split(" - ")[0]);

            Connection con = conexion.getInstancia().getConnection();
            String sql = "INSERT INTO grupo (idMateria, idProfesor, periodo) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMateria);
            ps.setInt(2, idProfesor);
            ps.setString(3, periodo);
            ps.executeUpdate();

            lblMensajeGrupo.setStyle("-fx-text-fill: green;");
            lblMensajeGrupo.setText("Grupo guardado");
            cmbMateriaGrupo.setValue(null); cmbProfesorGrupo.setValue(null); txtPeriodoGrupo.clear();
            cargarGrupos();
        } catch (SQLException e) {
            lblMensajeGrupo.setStyle("-fx-text-fill: red;");
            lblMensajeGrupo.setText("Error al guardar en BD");
        }
    }

    @FXML
    protected void eliminarGrupo() {
        grupo seleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "DELETE FROM grupo WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seleccionado.getId());
            ps.executeUpdate();
            cargarGrupos();
            lblMensajeGrupo.setStyle("-fx-text-fill: green;");
            lblMensajeGrupo.setText("Grupo eliminado");
        } catch (SQLException e) {
            lblMensajeGrupo.setStyle("-fx-text-fill: red;");
            lblMensajeGrupo.setText("Error: Calificaciones ligadas");
        }
    }

    @FXML
    protected void guardarCarrera() {
        String nombre = txtNombreCarrera.getText();
        if (nombre.isEmpty()) {
            lblMensajeCarrera.setStyle("-fx-text-fill: red;");
            lblMensajeCarrera.setText("Ingrese un nombre");
            return;
        }
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "INSERT INTO carrera (nombre) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();

            lblMensajeCarrera.setStyle("-fx-text-fill: green;");
            lblMensajeCarrera.setText("Carrera guardada");
            txtNombreCarrera.clear();
            cargarCatalogos();
        } catch (SQLException e) {
            lblMensajeCarrera.setStyle("-fx-text-fill: red;");
            lblMensajeCarrera.setText("Error BD: " + e.getMessage());
        }
    }

    @FXML
    protected void eliminarCarrera() {
        carrera seleccionada = tablaCarreras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "DELETE FROM carrera WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seleccionada.getId());
            ps.executeUpdate();
            cargarCatalogos();
            lblMensajeCarrera.setStyle("-fx-text-fill: green;");
            lblMensajeCarrera.setText("Carrera eliminada");
        } catch (SQLException e) {
            lblMensajeCarrera.setStyle("-fx-text-fill: red;");
            lblMensajeCarrera.setText("Error: Materias activas ligadas");
        }
    }

    @FXML
    protected void guardarSemestre() {
        String nombre = txtNombreSemestre.getText();
        if (nombre.isEmpty()) {
            lblMensajeSemestre.setStyle("-fx-text-fill: red;");
            lblMensajeSemestre.setText("Ingrese un nombre");
            return;
        }
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "INSERT INTO semestre (nombre) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();

            lblMensajeSemestre.setStyle("-fx-text-fill: green;");
            lblMensajeSemestre.setText("Semestre guardado");
            txtNombreSemestre.clear();
            cargarCatalogos();
        } catch (SQLException e) {
            lblMensajeSemestre.setStyle("-fx-text-fill: red;");
            lblMensajeSemestre.setText("Error BD: " + e.getMessage());
        }
    }

    @FXML
    protected void eliminarSemestre() {
        semestre seleccionado = tablaSemestres.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "DELETE FROM semestre WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seleccionado.getId());
            ps.executeUpdate();
            cargarCatalogos();
            lblMensajeSemestre.setStyle("-fx-text-fill: green;");
            lblMensajeSemestre.setText("Semestre eliminado");
        } catch (SQLException e) {
            lblMensajeSemestre.setStyle("-fx-text-fill: red;");
            lblMensajeSemestre.setText("Error: Materias activas ligadas");
        }
    }

    @FXML
    protected void actualizarEtiqueta() {
        String tipo = cmbTipo.getValue();
        if ("alumno".equals(tipo)) {
            txtDatoExtra.setPromptText("Escriba su Matricula");
        } else if ("profesor".equals(tipo)) {
            txtDatoExtra.setPromptText("Escriba No. de Empleado");
        }
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
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"), rs.getString("tipo")
                ));
            }
            tablaUsuarios.setItems(listaUsuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void guardarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();
        String tipo = cmbTipo.getValue();
        String datoExtra = txtDatoExtra.getText();
        String carreraSel = cmbCarreraUsuario.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || tipo == null || datoExtra.isEmpty()) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Llene todos los campos");
            return;
        }

        if ("alumno".equals(tipo) && carreraSel == null) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Seleccione una carrera para el alumno");
            return;
        }

        Connection con = null;
        try {
            con = conexion.getInstancia().getConnection();
            con.setAutoCommit(false);

            String sqlUsuario = "INSERT INTO usuario (nombre, correo, contrasena, tipo) VALUES (?, ?, ?, ?)";
            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, nombre);
            psUsuario.setString(2, correo);
            psUsuario.setString(3, contrasena);
            psUsuario.setString(4, tipo);
            psUsuario.executeUpdate();

            ResultSet rs = psUsuario.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            if ("alumno".equals(tipo)) {
                int idCarrera = Integer.parseInt(carreraSel.split(" - ")[0]);
                String sqlAlumno = "INSERT INTO alumno (id, matricula, idCarrera) VALUES (?, ?, ?)";
                PreparedStatement psAlumno = con.prepareStatement(sqlAlumno);
                psAlumno.setInt(1, idGenerado);
                psAlumno.setString(2, datoExtra);
                psAlumno.setInt(3, idCarrera);
                psAlumno.executeUpdate();
            } else if ("profesor".equals(tipo)) {
                String sqlProfesor = "INSERT INTO profesor (id, numeroEmpleado) VALUES (?, ?)";
                PreparedStatement psProfesor = con.prepareStatement(sqlProfesor);
                psProfesor.setInt(1, idGenerado);
                psProfesor.setString(2, datoExtra);
                psProfesor.executeUpdate();
            }

            con.commit();
            lblMensajeForm.setStyle("-fx-text-fill: green;");
            lblMensajeForm.setText("Usuario guardado con exito");
            txtNombre.clear(); txtCorreo.clear(); txtContrasena.clear(); txtDatoExtra.clear(); cmbTipo.setValue(null); cmbCarreraUsuario.setValue(null);
            cargarDatos();
            cargarListasGrupos();

        } catch (SQLException e) {
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Error en BD");
        } finally {
            if (con != null) { try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    @FXML
    protected void eliminarUsuario() {
        usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "DELETE FROM usuario WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seleccionado.getId());
            ps.executeUpdate();
            lblMensajeForm.setStyle("-fx-text-fill: green;");
            lblMensajeForm.setText("Usuario eliminado");
            cargarDatos();
            cargarListasGrupos();
        } catch (SQLException e) {
            lblMensajeForm.setStyle("-fx-text-fill: red;");
            lblMensajeForm.setText("Error al eliminar");
        }
    }

    @FXML
    protected void cargarMaterias() {
        ObservableList<materia> listaMaterias = FXCollections.observableArrayList();
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "SELECT id, nombre, idCarrera, idSemestre FROM materia";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listaMaterias.add(new materia(
                        rs.getInt("id"), rs.getString("nombre"), rs.getInt("idCarrera"), rs.getInt("idSemestre")
                ));
            }
            tablaMaterias.setItems(listaMaterias);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void guardarMateria() {
        String nombre = txtNombreMateria.getText();
        String carreraSeleccionada = cmbCarreraMateria.getValue();
        String semestreSeleccionado = cmbSemestreMateria.getValue();

        if (nombre.isEmpty() || carreraSeleccionada == null || semestreSeleccionado == null) {
            lblMensajeMateria.setStyle("-fx-text-fill: red;");
            lblMensajeMateria.setText("Llene todos los campos y menús");
            return;
        }

        try {
            int idCarrera = Integer.parseInt(carreraSeleccionada.split(" - ")[0]);
            int idSemestre = Integer.parseInt(semestreSeleccionado.split(" - ")[0]);

            Connection con = conexion.getInstancia().getConnection();
            String sql = "INSERT INTO materia (nombre, idCarrera, idSemestre) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, idCarrera);
            ps.setInt(3, idSemestre);
            ps.executeUpdate();

            lblMensajeMateria.setStyle("-fx-text-fill: green;");
            lblMensajeMateria.setText("Materia guardada");
            txtNombreMateria.clear();
            cmbCarreraMateria.setValue(null);
            cmbSemestreMateria.setValue(null);
            cargarMaterias();
            cargarListasGrupos();
        } catch (Exception e) {
            lblMensajeMateria.setStyle("-fx-text-fill: red;");
            lblMensajeMateria.setText("Error al guardar en BD");
        }
    }

    @FXML
    protected void eliminarMateria() {
        materia seleccionada = tablaMaterias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;
        try {
            Connection con = conexion.getInstancia().getConnection();
            String sql = "DELETE FROM materia WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seleccionada.getId());
            ps.executeUpdate();
            cargarMaterias();
            cargarListasGrupos();
        } catch (SQLException e) {
            lblMensajeMateria.setStyle("-fx-text-fill: red;");
            lblMensajeMateria.setText("Error al eliminar");
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