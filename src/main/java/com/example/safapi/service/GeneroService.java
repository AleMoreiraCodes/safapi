package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Estudio;
import com.example.safapi.model.entity.Genero;
import com.example.safapi.model.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class GeneroService {
    private GeneroRepository repository;

    public GeneroService(GeneroRepository repository) {
        this.repository = repository;
    }

    public List<Genero> getGeneros() {
        return repository.findAll();
    }

    public Optional<Genero> getGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Genero salvar(Genero genero) {
        validar(genero);
        return repository.save(genero);
    }

    @Transactional
    public void excluir(Genero estudio) {
        Objects.requireNonNull(estudio.getId());
        repository.delete(estudio);
    }

    public void validar(Genero genero) {
        if (genero.getNome() == null || genero.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (genero.getDescricao() == null || genero.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválido");
        }
    }
}
