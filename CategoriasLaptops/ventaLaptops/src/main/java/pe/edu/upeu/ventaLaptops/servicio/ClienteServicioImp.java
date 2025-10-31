package pe.edu.upeu.ventaLaptops.servicio.impl;

import org.springframework.stereotype.Service;
import pe.edu.upeu.ventaLaptops.modelo.Cliente;
import pe.edu.upeu.ventaLaptops.servicio.ClienteServicioI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ClienteServicioImp implements ClienteServicioI {

    private final List<Cliente> clientes = new ArrayList<>();
    private Long secuenciaId = 1L;

    @Override
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes); // copia para no exponer la lista original
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(secuenciaId++);
            clientes.add(cliente);
        } else {
            // actualizar datos si ya existe
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId().equals(cliente.getId())) {
                    clientes.set(i, cliente);
                    return cliente;
                }
            }
            clientes.add(cliente); // si no estaba, lo agregamos
        }
        return cliente;
    }

    @Override
    public void eliminarCliente(Long id) {
        Iterator<Cliente> it = clientes.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                break;
            }
        }
    }
}
