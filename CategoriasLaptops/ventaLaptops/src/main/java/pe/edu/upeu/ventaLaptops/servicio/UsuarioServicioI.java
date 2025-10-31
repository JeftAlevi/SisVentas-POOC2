package pe.edu.upeu.ventaLaptops.servicio;

import pe.edu.upeu.ventaLaptops.modelo.Usuario;
import java.util.List;

public interface UsuarioServicioI {

    String registrarUsuario(Usuario usuario);
    Usuario autenticarUsuario(String dni, String clave);
    List<Usuario> listarTodosLosUsuarios();
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuarioPorDni(String dni);
}