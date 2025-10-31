package pe.edu.upeu.ventaLaptops.servicio;

import pe.edu.upeu.ventaLaptops.modelo.Cliente;
import java.util.List;

public interface ClienteServicioI {
    List<Cliente> listarClientes();
    Cliente guardarCliente(Cliente cliente);
    void eliminarCliente(Long id);
}
