package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Estudio;
import com.example.safapi.model.entity.Favorito;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.repository.EstudioRepository;
import com.example.safapi.model.repository.FavoritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class EstudioService {
    private EstudioRepository repository;

    public EstudioService(EstudioRepository repository) {
        this.repository = repository;
    }

    public List<Estudio> getEstudios() {
        return repository.findAll();
    }

    public Optional<Estudio> getEstudioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Estudio salvar(Estudio estudio) {
        validar(estudio);
        return repository.save(estudio);
    }

    @Transactional
    public void excluir(Estudio estudio) {
        Objects.requireNonNull(estudio.getId());
        repository.delete(estudio);
    }

    public void validar(Estudio estudio) {
        if (estudio.getNome() == null || estudio.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (estudio.getPais() == null || estudio.getPais().trim().equals("")) {
            throw new RegraNegocioException("Pais inválido");
        }
        if (estudio.getDataFundacao() == null) {
            throw new RegraNegocioException("Data de fundação inválido");
        }
    }
}
