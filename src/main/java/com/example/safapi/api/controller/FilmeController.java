package com.example.safapi.api.controller;
import com.example.safapi.api.dto.FilmeDTO;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Estudio;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.service.EstudioService;
import com.example.safapi.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
@CrossOrigin

public class FilmeController {
    private final FilmeService service;
    private final EstudioService estudioService;

    @GetMapping()
    public ResponseEntity get() {
        List<Filme> filmes = service.getFilmes();
        return ResponseEntity.ok(filmes.stream().map(FilmeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.getFilmeById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filme.map(FilmeDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody FilmeDTO dto) {
        try {
            Filme filme = converter(dto);
            filme = service.salvar(filme);
            return new ResponseEntity(filme, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeDTO dto) {
        if (!service.getFilmeById(id).isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Filme filme = converter(dto);
            filme.setId(id);
            service.salvar(filme);
            return ResponseEntity.ok(filme);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.getFilmeById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filme.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Filme converter(FilmeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Filme filme = modelMapper.map(dto, Filme.class);
        if (dto.getIdEstudio() != null) {
            Optional<Estudio> estudio = estudioService.getEstudioById(dto.getIdEstudio());
            if (!estudio.isPresent()) {
                filme.setEstudio(null);
            } else {
                filme.setEstudio(estudio.get());
            }
        }
        return filme;
    }
}
