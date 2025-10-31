package pe.edu.upeu.ventaLaptops.modelo;

import lombok.Data;
import pe.edu.upeu.ventaLaptops.enums.Estado;

@Data
public class Marca {
    private int id;
    private String nombre;
    private Estado estado;
}