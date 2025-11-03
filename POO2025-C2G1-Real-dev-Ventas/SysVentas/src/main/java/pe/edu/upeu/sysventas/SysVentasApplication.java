package pe.edu.upeu.sysventas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SysVentasApplication extends Application {

    private ConfigurableApplicationContext ctx;
    private Parent parent;

    public static void main(String[] args) {
        launch(args); // JavaFX arranca aquí
    }

    @Override
    public void init() throws Exception {
        // 🔹 Inicializa el contexto de Spring
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SysVentasApplication.class);
        builder.application().setWebApplicationType(WebApplicationType.NONE);
        ctx = builder.run(getParameters().getRaw().toArray(new String[0]));

        // 🔹 Carga el FXML del login usando Spring
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_loguin.fxml"));
        loader.setControllerFactory(ctx::getBean); // importante para que use los controladores de Spring
        parent = loader.load();
    }

    @Override
    public void start(Stage stage) {
        // 🔹 Ajustar ventana
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(parent, bounds.getWidth(), bounds.getHeight() - 100);
        stage.setScene(scene);
        stage.setTitle("SysVentas - Login");
        stage.show();
    }

    @Override
    public void stop() {
        // 🔹 Cierra el contexto de Spring al salir
        if (ctx != null) {
            ctx.close();
        }
    }
}
