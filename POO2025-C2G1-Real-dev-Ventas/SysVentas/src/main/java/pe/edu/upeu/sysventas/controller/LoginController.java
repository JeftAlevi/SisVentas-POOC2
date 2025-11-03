package pe.edu.upeu.sysventas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    @Autowired
    private ApplicationContext context; // 🔹 Spring context

    @FXML
    private TextField txtUsuario;

    @FXML
    private Button btnLogin;

    @FXML
    private void onMouseEnter() {
        btnLogin.setStyle("-fx-background-color: linear-gradient(to right, #1e3c72, #2a5298); -fx-text-fill: white; -fx-font-weight: bold;");
    }

    @FXML
    private void onMouseExit() {
        btnLogin.setStyle("-fx-background-color: linear-gradient(to right, #2980b9, #6dd5fa); -fx-text-fill: white; -fx-font-weight: bold;");
    }

    @FXML
    private PasswordField txtClave;

    @FXML
    void onLogin() {
        String user = txtUsuario.getText();
        String clave = txtClave.getText();

        if ("admin".equals(user) && "123".equals(clave)) {
            try {
                // Mensaje de bienvenida
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Acceso correcto");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenido, " + user);
                alert.showAndWait();

                // 🔹 Cargar MainMenu.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
                loader.setControllerFactory(context::getBean); // Spring inyecta el controlador
                Parent root = loader.load();

                // 🔹 Cambiar escena actual
                Stage stage = (Stage) txtUsuario.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("SysVentas - Menú Principal");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de acceso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos");
            alert.showAndWait();
        }
    }
}
