package pe.edu.upeu.ventaLaptops.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.ventaLaptops.enums.Estado;
import pe.edu.upeu.ventaLaptops.modelo.Cliente;
import pe.edu.upeu.ventaLaptops.servicio.ClienteServicioI;
import pe.edu.upeu.ventaLaptops.utils.NavegadorVistas;
import pe.edu.upeu.ventaLaptops.utils.Vistas;

@Controller
public class ClientesController {

    @Autowired
    private ClienteServicioI clienteServicio;
    @Autowired
    private NavegadorVistas navegador;

    // Tabla
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, Estado> colEstado;

    // Panel edición
    @FXML private VBox panelEdicion;
    @FXML private TextField nombreField;
    @FXML private TextField emailField;
    @FXML private TextField telefonoField;
    @FXML private RadioButton activoRadio;
    @FXML private RadioButton inactivoRadio;

    private ToggleGroup estadoGroup;
    private ObservableList<Cliente> listaObservableClientes;
    private Cliente clienteSeleccionado;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarClientes();
        panelEdicion.setDisable(true);

        estadoGroup = new ToggleGroup();
        activoRadio.setToggleGroup(estadoGroup);
        inactivoRadio.setToggleGroup(estadoGroup);
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Mostrar Activo / Inactivo
        colEstado.setCellFactory(c -> new TableCell<Cliente, Estado>() {
            @Override
            protected void updateItem(Estado item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item == Estado.ACTIVO ? "Activo" : "Inactivo");
                }
            }
        });

        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            this.clienteSeleccionado = sel;
            if (sel != null) {
                mostrarDatosEnPanel(sel);
                panelEdicion.setDisable(false);
            } else {
                limpiarPanel();
                panelEdicion.setDisable(true);
            }
        });
    }

    private void mostrarDatosEnPanel(Cliente cliente) {
        nombreField.setText(cliente.getNombre());
        emailField.setText(cliente.getEmail());
        telefonoField.setText(cliente.getTelefono());
        if (cliente.getEstado() == Estado.ACTIVO) {
            activoRadio.setSelected(true);
        } else {
            inactivoRadio.setSelected(true);
        }
    }

    private void limpiarPanel() {
        nombreField.clear();
        emailField.clear();
        telefonoField.clear();
        estadoGroup.selectToggle(null);
    }

    private void cargarClientes() {
        listaObservableClientes = FXCollections.observableArrayList(clienteServicio.listarClientes());
        tablaClientes.setItems(listaObservableClientes);
        tablaClientes.refresh();
    }

    @FXML
    void nuevoAccion(ActionEvent event) {
        tablaClientes.getSelectionModel().clearSelection();
        clienteSeleccionado = null;
        limpiarPanel();
        activoRadio.setSelected(true);
        panelEdicion.setDisable(false);
        nombreField.requestFocus();
    }

    @FXML
    void guardarAccion(ActionEvent event) {
        String nombre = nombreField.getText();
        String email = emailField.getText();
        String telefono = telefonoField.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre no puede estar vacío.");
            return;
        }
        if (email == null || email.trim().isEmpty()) {
            mostrarAlerta("Error", "El email no puede estar vacío.");
            return;
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono no puede estar vacío.");
            return;
        }

        Estado estadoSeleccionado = activoRadio.isSelected() ? Estado.ACTIVO : Estado.INACTIVO;

        if (clienteSeleccionado != null) {
            clienteSeleccionado.setNombre(nombre);
            clienteSeleccionado.setEmail(email);
            clienteSeleccionado.setTelefono(telefono);
            clienteSeleccionado.setEstado(estadoSeleccionado);
            clienteServicio.guardarCliente(clienteSeleccionado);
            mostrarAlerta("Éxito", "Cliente actualizado correctamente.");
        } else {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(nombre);
            nuevoCliente.setEmail(email);
            nuevoCliente.setTelefono(telefono);
            nuevoCliente.setEstado(estadoSeleccionado);
            clienteServicio.guardarCliente(nuevoCliente);
            mostrarAlerta("Éxito", "Cliente creado correctamente.");
        }

        cargarClientes();
        tablaClientes.getSelectionModel().clearSelection();
    }

    @FXML
    void editarAccion(ActionEvent event) {
        Cliente seleccionada = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar un cliente para editar.");
            return;
        }
        mostrarDatosEnPanel(seleccionada);
        panelEdicion.setDisable(false);
        clienteSeleccionado = seleccionada;
        nombreField.requestFocus();
    }

    @FXML
    void eliminarAccion(ActionEvent event) {
        Cliente seleccionada = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar un cliente.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Eliminar al cliente: " + seleccionada.getNombre() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                clienteServicio.eliminarCliente(seleccionada.getId());
                cargarClientes();
            }
        });
    }

    @FXML
    void volverAccion(ActionEvent event) {
        Node sourceNode = (Node) event.getSource();
        navegador.cambiarEscena(sourceNode, Vistas.MENU_ADMIN, "Panel de Administrador");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


