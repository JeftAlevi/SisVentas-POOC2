package pe.edu.upeu.ventaLaptops.servicio;

import pe.edu.upeu.ventaLaptops.modelo.Marca;
import java.util.List;

public interface MarcaServicioI {
    List<Marca> listarMarcas();
    Marca guardarMarca(Marca marca);
    void eliminarMarca(int id);
}
