package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Direcao;
import com.example.safapi.model.repository.DirecaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class DirecaoService {
    private DirecaoRepository repository;

    public DirecaoService(DirecaoRepository repository) {
        this.repository = repository;
    }

    public List<Direcao> getDirecoes() {
        return repository.findAll();
    }

    public Optional<Direcao> getDirecaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Direcao salvar(Direcao direcao) {
        validar(direcao);
        return repository.save(direcao);
    }

    @Transactional
    public void excluir(Direcao direcao) {
        Objects.requireNonNull(direcao.getId());
        repository.delete(direcao);
    }

    public void validar(Direcao direcao) {
        if (direcao.getDiretor() == null || direcao.getDiretor().getId() == null || direcao.getDiretor().getId() == 0) {
            throw new RegraNegocioException("Diretor inválido");
        }
        if (direcao.getFilme() == null || direcao.getFilme() .getId() == null || direcao.getFilme().getId() == 0) {
            throw new RegraNegocioException("Filme inválido");
        }
    }
}
