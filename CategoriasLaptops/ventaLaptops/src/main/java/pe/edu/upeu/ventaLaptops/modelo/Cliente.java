package pe.edu.upeu.ventaLaptops.modelo;

import lombok.Data;
import pe.edu.upeu.ventaLaptops.enums.Estado;

@Data
public class Cliente {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Estado estado;
}

