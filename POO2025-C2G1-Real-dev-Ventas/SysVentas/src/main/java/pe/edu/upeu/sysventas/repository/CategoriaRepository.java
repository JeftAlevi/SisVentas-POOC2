package pe.edu.upeu.sysventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
