package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Papel;
import com.example.safapi.model.entity.Usuario;
import com.example.safapi.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> getUsuarios() {
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        validar(usuario);
        return repository.save(usuario);
    }

    @Transactional
    public void excluir(Usuario usuario) {
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }

    public void validar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (usuario.getNacionalidade() == null || usuario.getNacionalidade().trim().equals("")) {
            throw new RegraNegocioException("Nacionalidade inválido");
        }
        if (usuario.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de Nascimento inválido");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().equals("")) {
            throw new RegraNegocioException("Senha inválido");
        }
        if (usuario.getTelefone() == null || usuario.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (usuario.getAdministrador() == null) {
            throw new RegraNegocioException("Administrador inválido");
        }
    }
}
