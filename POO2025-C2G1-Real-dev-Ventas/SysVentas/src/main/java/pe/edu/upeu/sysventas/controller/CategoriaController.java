package pe.edu.upeu.sysventas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pe.edu.upeu.sysventas.DAO.CategoriaDAO;
import pe.edu.upeu.sysventas.model.Categoria;

public class CategoriaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TableView<Categoria> tablaCategoria;

    @FXML
    private TableColumn<Categoria, Long> colId;

    @FXML
    private TableColumn<Categoria, String> colNombre;

    @FXML
    private Button btnVolver;

    private ObservableList<Categoria> listaCategorias;
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();


    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cargarCategorias(); // esto carga desde SQLite
    }


    private void cargarCategorias() {
        listaCategorias = FXCollections.observableArrayList(categoriaDAO.listar());
        tablaCategoria.setItems(listaCategorias);
    }

    @FXML
    void onGuardar() {
        String nombre = txtNombre.getText().trim();
        if (!nombre.isEmpty()) {
            Categoria c = new Categoria();
            c.setNombre(nombre);
            categoriaDAO.insertar(c);
            txtNombre.clear();
            cargarCategorias(); // refresca la tabla
        } else {
            mostrarAlerta("Debe ingresar un nombre de categoría");
        }
    }

    @FXML
    void onEliminar() {
        Categoria seleccion = tablaCategoria.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            categoriaDAO.eliminar(seleccion.getIdCategoria());
            cargarCategorias();
        } else {
            mostrarAlerta("Debe seleccionar una categoría para eliminar");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void volverAlMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Principal - SysVentas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
