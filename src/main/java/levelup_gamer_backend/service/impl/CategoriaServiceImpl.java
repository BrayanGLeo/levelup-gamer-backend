package levelup_gamer_backend.service.impl;

import levelup_gamer_backend.entity.Categoria;
import levelup_gamer_backend.repository.CategoriaRepository;
import levelup_gamer_backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("null")
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        Optional<Categoria> existingCat = categoriaRepository.findByNombre(categoria.getNombre());
        if (existingCat.isPresent()
                && (categoria.getId() == null || !existingCat.get().getId().equals(categoria.getId()))) {
            throw new RuntimeException("Ya existe una categoría con ese nombre: " + categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        categoriaRepository.deleteById(id);
    }
}