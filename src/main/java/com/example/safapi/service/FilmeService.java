package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Favorito;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class FilmeService {
    private FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> getFilmes() {
        return repository.findAll();
    }

    public Optional<Filme> getFilmeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Filme salvar(Filme filme) {
        validar(filme);
        return repository.save(filme);
    }

    @Transactional
    public void excluir(Filme filme) {
        Objects.requireNonNull(filme.getId());
        repository.delete(filme);
    }

    public void validar(Filme filme) {
        if (filme.getEstudio() == null || filme.getEstudio().getId() == null || filme.getEstudio().getId() == 0) {
            throw new RegraNegocioException("Estúdio inválido");
        }
        if (filme.getTitulo() == null || filme.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo inválido");
        }
        if (filme.getSinopse() == null || filme.getSinopse().trim().equals("")) {
            throw new RegraNegocioException("Sinopse inválido");
        }
        if (filme.getDataLancamento() == null) {
            throw new RegraNegocioException("Data de lançamento inválido");
        }
        if (filme.getDuracao() == null || filme.getDuracao() <= 0.0) {
            throw new RegraNegocioException("Duração inválida");
        }

        if (filme.getImdb() == null || filme.getDuracao() < 0.0) {
            throw new RegraNegocioException("Imdb inválida");
        }
    }
}
