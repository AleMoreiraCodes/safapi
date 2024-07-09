package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Avaliacao;
import com.example.safapi.model.entity.Direcao;
import com.example.safapi.model.repository.AvaliacaoRepository;
import com.example.safapi.model.repository.DirecaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class AvaliacaoService {
    private AvaliacaoRepository repository;

    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public List<Avaliacao> getAvaliacoes() {
        return repository.findAll();
    }

    public Optional<Avaliacao> getAvaliacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Avaliacao salvar(Avaliacao avaliacao) {
        validar(avaliacao);
        return repository.save(avaliacao);
    }

    @Transactional
    public void excluir(Avaliacao avaliacao) {
        Objects.requireNonNull(avaliacao.getId());
        repository.delete(avaliacao);
    }

    public void validar(Avaliacao avaliacao) {
        if (avaliacao.getUsuario() == null || avaliacao.getUsuario().getId() == null || avaliacao.getUsuario() .getId() == 0) {
            throw new RegraNegocioException("Usuário inválido");
        }
        if (avaliacao.getFilme() == null || avaliacao.getFilme().getId() == null || avaliacao.getFilme().getId() == 0) {
            throw new RegraNegocioException("Filme inválido");
        }
        if (avaliacao.getComentario() == null || avaliacao.getComentario().trim().equals("")) {
            throw new RegraNegocioException("Comentário inválido");
        }
        if (avaliacao.getNota() == null || avaliacao.getNota() < 0.0) {
            throw new RegraNegocioException("Nota inválida");
        }

    }
}
