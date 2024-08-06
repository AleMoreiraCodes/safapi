package com.example.safapi.api.controller;
import com.example.safapi.api.dto.FilmeGeneroDTO;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.entity.FilmeGenero;
import com.example.safapi.model.entity.Genero;
import com.example.safapi.service.FilmeGeneroService;
import com.example.safapi.service.FilmeService;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmesGeneros")
@RequiredArgsConstructor
@CrossOrigin

public class FilmeGeneroController {
    private final FilmeGeneroService service;
    private final GeneroService generoService;
    private final FilmeService filmeService;

    @GetMapping()
    public ResponseEntity get() {
        List<FilmeGenero> filmesGeneros = service.getFilmeGeneros();
        return ResponseEntity.ok(filmesGeneros.stream().map(FilmeGeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FilmeGenero> filmeGenero = service.getFilmeGeneroById(id);
        if (!filmeGenero.isPresent()) {
            return new ResponseEntity("FilmeGênero não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filmeGenero.map(FilmeGeneroDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody FilmeGeneroDTO dto) {
        try {
            FilmeGenero filmeGenero = converter(dto);
            filmeGenero = service.salvar(filmeGenero);
            return new ResponseEntity(filmeGenero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeGeneroDTO dto) {
        if (!service.getFilmeGeneroById(id).isPresent()) {
            return new ResponseEntity("FilmeGênero não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            FilmeGenero filmeGenero = converter(dto);
            filmeGenero.setId(id);
            service.salvar(filmeGenero);
            return ResponseEntity.ok(filmeGenero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FilmeGenero> filmeGenero = service.getFilmeGeneroById(id);
        if (!filmeGenero.isPresent()) {
            return new ResponseEntity("FilmeGênero não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filmeGenero.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FilmeGenero converter(FilmeGeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeGenero filmeGenero = modelMapper.map(dto, FilmeGenero.class);
        if (dto.getIdGenero() != null) {
            Optional<Genero> genero = generoService.getGeneroById(dto.getIdGenero());
            if (!genero.isPresent()) {
                filmeGenero.setGenero(null);
            } else {
                filmeGenero.setGenero(genero.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                filmeGenero.setFilme(null);
            } else {
                filmeGenero.setFilme(filme.get());
            }
        }
        return filmeGenero;
    }
}
