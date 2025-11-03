package pe.edu.upeu.sysventas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.upeu.sysventas.DAO.ClientesDAO;
import pe.edu.upeu.sysventas.model.Cliente;

public class ClientesController {

    @FXML private TextField nombreField;
    @FXML private TextField apellidosField;
    @FXML private TextField direccionField;
    @FXML private TextField dniField;
    @FXML private TextField telefonoField;

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Long> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colDireccion;
    @FXML private TableColumn<Cliente, String> colDni;
    @FXML private TableColumn<Cliente, String> colTelefono;

    @FXML private Button btnVolver;

    private ObservableList<Cliente> listaClientes;
    private final ClientesDAO clienteDAO = new ClientesDAO();
    private Cliente clienteSeleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        cargarClientes();

        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            clienteSeleccionado = newSel;
            if (newSel != null) {
                mostrarDatosEnFormulario(newSel);
            } else {
                limpiarFormulario();
            }
        });
    }

    private void cargarClientes() {
        listaClientes = FXCollections.observableArrayList(clienteDAO.listar());
        tablaClientes.setItems(listaClientes);
    }

    private void mostrarDatosEnFormulario(Cliente c) {
        nombreField.setText(c.getNombre());
        apellidosField.setText(c.getApellidos());
        direccionField.setText(c.getDireccion());
        dniField.setText(c.getDni());
        telefonoField.setText(c.getTelefono());
    }

    private void limpiarFormulario() {
        nombreField.clear();
        apellidosField.clear();
        direccionField.clear();
        dniField.clear();
        telefonoField.clear();
        clienteSeleccionado = null;
    }

    @FXML
    void nuevoAccion() {
        limpiarFormulario();
    }

    @FXML
    void guardarAccion() {
        String nombre = nombreField.getText().trim();
        String apellidos = apellidosField.getText().trim();
        String direccion = direccionField.getText().trim();
        String dni = dniField.getText().trim();
        String telefono = telefonoField.getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty()) {
            mostrarAlerta("Debe ingresar nombre y apellidos del cliente");
            return;
        }

        if (clienteSeleccionado != null) {
            // Editar cliente existente
            clienteSeleccionado.setNombre(nombre);
            clienteSeleccionado.setApellidos(apellidos);
            clienteSeleccionado.setDireccion(direccion);
            clienteSeleccionado.setDni(dni);
            clienteSeleccionado.setTelefono(telefono);
            clienteDAO.actualizar(clienteSeleccionado);
        } else {
            // Nuevo cliente
            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setApellidos(apellidos);
            c.setDireccion(direccion);
            c.setDni(dni);
            c.setTelefono(telefono);
            clienteDAO.insertar(c);
        }

        limpiarFormulario();
        cargarClientes();
    }

    @FXML
    void eliminarAccion() {
        if (clienteSeleccionado != null) {
            clienteDAO.eliminar(clienteSeleccionado.getId());
            limpiarFormulario();
            cargarClientes();
        } else {
            mostrarAlerta("Debe seleccionar un cliente para eliminar");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void volverAccion() {
        // Aquí colocas la lógica para volver al menú, como en tu CategoriaController
    }
}
