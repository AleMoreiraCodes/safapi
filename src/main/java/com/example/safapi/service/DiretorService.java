package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Diretor;
import com.example.safapi.model.repository.DiretorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiretorService {
    private DiretorRepository repository;

    public DiretorService(DiretorRepository repository) {
        this.repository = repository;
    }

    public List<Diretor> getDiretores() {
        return repository.findAll();
    }

    public Optional<Diretor> getDiretorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Diretor salvar(Diretor diretor) {
        validar(diretor);
        return repository.save(diretor);
    }

    @Transactional
    public void excluir(Diretor diretor) {
        Objects.requireNonNull(diretor.getId());
        repository.delete(diretor);
    }

    public void validar(Diretor diretor) {
        if (diretor.getNome() == null || diretor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (diretor.getNacionalidade() == null || diretor.getNacionalidade().trim().equals("")) {
            throw new RegraNegocioException("Nacionalidade inválido");
        }
        if (diretor.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de Nascimento inválido");
        }
        if (diretor.getDataInicioCarreira() == null) {
            throw new RegraNegocioException("Data de início de carreira inválido");
        }
    }
}
