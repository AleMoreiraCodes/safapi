package com.example.safapi.api.controller;

import com.example.safapi.api.dto.AvaliacaoDTO;
import com.example.safapi.model.entity.Avaliacao;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.entity.Usuario;
import com.example.safapi.service.AvaliacaoService;
import com.example.safapi.service.FilmeService;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/avaliacoes")
@RequiredArgsConstructor
@CrossOrigin

public class AvaliacaoController {
    private final AvaliacaoService service;
    private final UsuarioService usuarioService;
    private final FilmeService filmeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Avaliacao> avaliacoes = service.getAvaliacoes();
        return ResponseEntity.ok(avaliacoes.stream().map(AvaliacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Avaliacao> avaliacao = service.getAvaliacaoById(id);
        if (!avaliacao.isPresent()) {
            return new ResponseEntity("Avaliação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(avaliacao.map(AvaliacaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AvaliacaoDTO dto) {
        try {
            Avaliacao avaliacao = converter(dto);
            avaliacao = service.salvar(avaliacao);
            return new ResponseEntity(avaliacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AvaliacaoDTO dto) {
        if (!service.getAvaliacaoById(id).isPresent()) {
            return new ResponseEntity("Avaliação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Avaliacao avaliacao = converter(dto);
            avaliacao.setId(id);
            service.salvar(avaliacao);
            return ResponseEntity.ok(avaliacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Avaliacao> avaliacao = service.getAvaliacaoById(id);
        if (!avaliacao.isPresent()) {
            return new ResponseEntity("Avaliação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(avaliacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Avaliacao converter(AvaliacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Avaliacao avaliacao = modelMapper.map(dto, Avaliacao.class);
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(dto.getIdUsuario());
            if (!usuario.isPresent()) {
                avaliacao.setUsuario(null);
            } else {
                avaliacao.setUsuario(usuario.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                avaliacao.setFilme(null);
            } else {
                avaliacao.setFilme(filme.get());
            }
        }
        return avaliacao;
    }
}
