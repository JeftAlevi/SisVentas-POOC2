package pe.edu.upeu.sysventas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    @FXML
    void abrirProductos() {
        abrirVentana("/view/Producto.fxml", "Productos - SysVentas");
    }

    @FXML
    void abrirCategorias() {
        abrirVentana("/view/Categoria.fxml", "Categorías - SysVentas");
    }

    @FXML
    void salirSistema() {
        System.exit(0);
    }

    private void abrirVentana(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(cl -> {
                try {
                    return cl.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
