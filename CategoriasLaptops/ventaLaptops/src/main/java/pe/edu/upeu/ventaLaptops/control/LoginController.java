package pe.edu.upeu.ventaLaptops.control;
import javafx.fxml.FXML;

import pe.edu.upeu.ventaLaptops.VentaLaptopsApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.ventaLaptops.enums.RolUsuario;
import pe.edu.upeu.ventaLaptops.modelo.Usuario;
import pe.edu.upeu.ventaLaptops.servicio.UsuarioServicioI;
import pe.edu.upeu.ventaLaptops.utils.NavegadorVistas;
import pe.edu.upeu.ventaLaptops.utils.Vistas;

@Controller
public class LoginController {

    @Autowired
    private NavegadorVistas navegador;
    @Autowired
    private UsuarioServicioI usuarioServicio;

    @FXML
    private TextField usuarioTextField; // Cambiado de dniTextField
    @FXML
    private PasswordField clavePasswordField;
    @FXML
    private Label mensajeLabel;

    @FXML
    void ingresarAccion(ActionEvent event) {
        String usuarioStr = usuarioTextField.getText();
        String clave = clavePasswordField.getText();

        if (usuarioStr.isEmpty() || clave.isEmpty()) {
            mensajeLabel.setText("El USUARIO y la contraseña son obligatorios.");
            return;
        }

        Usuario usuario = usuarioServicio.autenticarUsuario(usuarioStr, clave);

        if (usuario != null) {
            VentaLaptopsApplication.usuarioLogueado = usuario;
            Node sourceNode = (Node) event.getSource();
            if (usuario.getRol() == RolUsuario.ADMINISTRADOR) {
                navegador.cambiarEscena(sourceNode, Vistas.MENU_ADMIN, "Panel de Administrador");
            } else if (usuario.getRol() == RolUsuario.CLIENTE) {
                navegador.cambiarEscena(sourceNode, Vistas.MENU_CLIENTE, "Bienvenido Cliente");
            }
        } else {
            mensajeLabel.setText("USUARIO o contraseña incorrectos.");
        }
    }

    @FXML
    void salirAccion(ActionEvent event) {
        Platform.exit();
    }
}
