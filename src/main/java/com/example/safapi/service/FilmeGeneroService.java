package com.example.safapi.service;

import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.entity.FilmeGenero;
import com.example.safapi.model.repository.FilmeGeneroRepository;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmeGeneroService {

    private FilmeGeneroRepository repository;

    public FilmeGeneroService(FilmeGeneroRepository repository) {
        this.repository = repository;
    }

    public List<FilmeGenero> getFilmeGeneros() {
        return repository.findAll();
    }

    public Optional<FilmeGenero> getFilmeGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public FilmeGenero salvar(FilmeGenero filmeGenero) {
        validar(filmeGenero);
        return repository.save(filmeGenero);
    }

    @Transactional
    public void excluir(FilmeGenero filmeGenero) {
        Objects.requireNonNull(filmeGenero.getId());
        repository.delete(filmeGenero);
    }

    public void validar(FilmeGenero filmeGenero) {
        if (filmeGenero.getGenero() == null || filmeGenero.getGenero().getId() == null || filmeGenero.getGenero().getId() == 0) {
            throw new RegraNegocioException("Gênero inválido");
        }
        if (filmeGenero.getFilme() == null || filmeGenero.getFilme() .getId() == null || filmeGenero.getFilme().getId() == 0) {
            throw new RegraNegocioException("Filme inválido");
        }
    }
}
