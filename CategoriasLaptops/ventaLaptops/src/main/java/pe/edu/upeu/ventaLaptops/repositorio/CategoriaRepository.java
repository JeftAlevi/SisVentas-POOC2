package pe.edu.upeu.ventaLaptops.repositorio;

import pe.edu.upeu.ventaLaptops.modelo.Categoria;
import pe.edu.upeu.ventaLaptops.enums.Estado;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {

    protected List<Categoria> listaCategorias = new ArrayList<>();
    private int proximoId = 1;

    public CategoriaRepository() {
        // Categorías iniciales con laptops comunes en Perú
        guardar(new Categoria() {{ setNombre("Lenovo"); setEstado(Estado.ACTIVO); }});
        guardar(new Categoria() {{ setNombre("HP"); setEstado(Estado.ACTIVO); }});
        guardar(new Categoria() {{ setNombre("Dell"); setEstado(Estado.ACTIVO); }});
        guardar(new Categoria() {{ setNombre("Acer"); setEstado(Estado.ACTIVO); }});
        guardar(new Categoria() {{ setNombre("Asus"); setEstado(Estado.INACTIVO); }});
        guardar(new Categoria() {{ setNombre("Apple (MacBook)"); setEstado(Estado.ACTIVO); }});
    }

    public List<Categoria> buscarTodos() {
        return new ArrayList<>(listaCategorias);
    }

    public Categoria guardar(Categoria categoria) {
        if (categoria.getId() == 0) {
            categoria.setId(proximoId++);
            listaCategorias.add(categoria);
        }
        else {
            listaCategorias.stream()
                    .filter(c -> c.getId() == categoria.getId())
                    .findFirst()
                    .ifPresent(c -> {
                        c.setNombre(categoria.getNombre());
                        c.setEstado(categoria.getEstado());
                    });
        }
        return categoria;
    }

    public void eliminar(int id) {
        listaCategorias.removeIf(c -> c.getId() == id);
    }
}
