package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Estudio;
import com.example.safapi.model.entity.Papel;
import com.example.safapi.model.repository.PapelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class PapelService {
    private PapelRepository repository;

    public PapelService(PapelRepository repository) {
        this.repository = repository;
    }

    public List<Papel> getPapeis() {
        return repository.findAll();
    }

    public Optional<Papel> getPapelById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Papel salvar(Papel papel) {
        validar(papel);
        return repository.save(papel);
    }

    @Transactional
    public void excluir(Papel papel) {
        Objects.requireNonNull(papel.getId());
        repository.delete(papel);
    }

    public void validar(Papel papel) {
        if (papel.getNome() == null || papel.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (papel.getFilme() == null || papel.getFilme().getId() == null || papel.getFilme().getId() == 0) {
            throw new RegraNegocioException("Filme inválido");
        }

        if (papel.getAtor() == null || papel.getAtor().getId() == null || papel.getAtor().getId() == 0) {
            throw new RegraNegocioException("Ator inválido");
        }

    }
}
