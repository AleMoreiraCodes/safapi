package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Ator;
import com.example.safapi.model.entity.Usuario;
import com.example.safapi.model.repository.AtorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class AtorService {
    private AtorRepository repository;

    public AtorService(AtorRepository repository) {
        this.repository = repository;
    }

    public List<Ator> getAtores() {
        return repository.findAll();
    }

    public Optional<Ator> getAtorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Ator salvar(Ator ator) {
        validar(ator);
        return repository.save(ator);
    }

    @Transactional
    public void excluir(Ator ator) {
        Objects.requireNonNull(ator.getId());
        repository.delete(ator);
    }

    public void validar(Ator ator) {
        if (ator.getNome() == null || ator.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (ator.getNacionalidade() == null || ator.getNacionalidade().trim().equals("")) {
            throw new RegraNegocioException("Nacionalidade inválido");
        }
        if (ator.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de Nascimento inválido");
        }
        if (ator.getDataInicioCarreira() == null) {
            throw new RegraNegocioException("Data de início de carreira inválido");
        }
    }
}
