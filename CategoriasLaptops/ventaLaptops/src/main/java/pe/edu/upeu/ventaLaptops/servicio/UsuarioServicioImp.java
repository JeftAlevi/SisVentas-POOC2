package pe.edu.upeu.ventaLaptops.servicio;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pe.edu.upeu.ventaLaptops.enums.Estado;
import pe.edu.upeu.ventaLaptops.enums.RolUsuario;
import pe.edu.upeu.ventaLaptops.modelo.Usuario;
import pe.edu.upeu.ventaLaptops.repositorio.UsuarioRepository;
import pe.edu.upeu.ventaLaptops.utils.ValidacionesUtils;

import java.util.List;

@Service
public class UsuarioServicioImp extends UsuarioRepository implements UsuarioServicioI {

    // 🔹 Se ejecuta automáticamente cuando la app arranca
    @PostConstruct
    public void init() {
        if (buscarPorDni("admin") == null) {
            Usuario admin = new Usuario();
            admin.setDni("admin");
            admin.setClave("123");
            admin.setRol(RolUsuario.ADMINISTRADOR);
            admin.setEstado(Estado.ACTIVO);
            this.guardar(admin);
        }
    }

    @Override
    public String registrarUsuario(Usuario usuario) {
        if (!ValidacionesUtils.validarDni(usuario.getDni())) {
            return "Error: El DNI debe tener 8 dígitos numéricos.";
        }
        if (!ValidacionesUtils.validarClave(usuario.getClave())) {
            return "Error: La clave debe tener más de 8 caracteres, incluyendo letras y números.";
        }
        if (this.buscarPorDni(usuario.getDni()) != null) {
            return "Error: El DNI ya se encuentra registrado.";
        }
        usuario.setEstado(Estado.ACTIVO);
        this.guardarUsuario(usuario);
        return "Registro exitoso.";
    }

    @Override
    public Usuario autenticarUsuario(String dni, String clave) {
        Usuario usuario = this.buscarPorDni(dni);
        if (usuario != null && usuario.getClave().equals(clave) && usuario.getEstado() == Estado.ACTIVO) {
            return usuario;
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return this.listaUsuarios;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        if (buscarPorDni(usuario.getDni()) == null) {
            this.guardar(usuario);
        }
        return usuario;
    }

    @Override
    public void eliminarUsuarioPorDni(String dni) {
        this.listaUsuarios.removeIf(user -> user.getDni().equals(dni));
    }
}
