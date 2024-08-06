package com.example.safapi.api.controller;

import com.example.safapi.api.dto.FavoritoDTO;
import com.example.safapi.model.entity.Favorito;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.entity.Usuario;
import com.example.safapi.service.FavoritoService;
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
@RequestMapping("/api/v1/favoritos")
@RequiredArgsConstructor
@CrossOrigin

public class FavoritoController {
    private final FavoritoService service;
    private final UsuarioService usuarioService;
    private final FilmeService filmeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Favorito> favoritos = service.getFavoritos();
        return ResponseEntity.ok(favoritos.stream().map(FavoritoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Favorito> favorito = service.getFavoritoById(id);
        if (!favorito.isPresent()) {
            return new ResponseEntity("Favorito não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(favorito.map(FavoritoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody FavoritoDTO dto) {
        try {
            Favorito favorito = converter(dto);
            favorito = service.salvar(favorito);
            return new ResponseEntity(favorito, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FavoritoDTO dto) {
        if (!service.getFavoritoById(id).isPresent()) {
            return new ResponseEntity("Favorito não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Favorito favorito = converter(dto);
            favorito.setId(id);
            service.salvar(favorito);
            return ResponseEntity.ok(favorito);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Favorito> favorito = service.getFavoritoById(id);
        if (!favorito.isPresent()) {
            return new ResponseEntity("Favorito não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(favorito.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Favorito converter(FavoritoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Favorito favorito = modelMapper.map(dto, Favorito.class);
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(dto.getIdUsuario());
            if (!usuario.isPresent()) {
                favorito.setUsuario(null);
            } else {
                favorito.setUsuario(usuario.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                favorito.setFilme(null);
            } else {
                favorito.setFilme(filme.get());
            }
        }
        return favorito;
    }
}
