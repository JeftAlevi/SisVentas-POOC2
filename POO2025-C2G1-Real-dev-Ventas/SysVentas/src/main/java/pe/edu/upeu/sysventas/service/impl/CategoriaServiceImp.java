package pe.edu.upeu.sysventas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.dto.ComboBoxOption;
import pe.edu.upeu.sysventas.model.Categoria;
import pe.edu.upeu.sysventas.repository.CategoriaRepository;
import pe.edu.upeu.sysventas.service.ICategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria save(Categoria c) {
        return categoriaRepository.save(c);
    }

    @Override
    public Categoria update(Long id, Categoria c) {
        c.setIdCategoria(id);
        return categoriaRepository.save(c);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public void delete(Categoria c) {
        categoriaRepository.delete(c);
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        return categoriaRepository.findAll()
                .stream()
                .map(c -> new ComboBoxOption(String.valueOf(c.getIdCategoria()), c.getNombre()))
                .collect(Collectors.toList());
    }
}
