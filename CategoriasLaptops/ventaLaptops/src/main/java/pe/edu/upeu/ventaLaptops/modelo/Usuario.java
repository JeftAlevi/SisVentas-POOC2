package pe.edu.upeu.ventaLaptops.modelo;

import lombok.Data;
import pe.edu.upeu.ventaLaptops.enums.Estado;
import pe.edu.upeu.ventaLaptops.enums.RolUsuario;

@Data
public class Usuario {

    private String dni;
    private String clave;
    private Estado estado;
    private RolUsuario rol;

}