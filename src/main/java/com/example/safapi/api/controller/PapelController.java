package com.example.safapi.api.controller;
import com.example.safapi.api.dto.PapelDTO;
import com.example.safapi.model.entity.Ator;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.entity.Papel;
import com.example.safapi.service.AtorService;
import com.example.safapi.service.PapelService;
import com.example.safapi.service.FilmeService;
import com.example.safapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/papeis")
@RequiredArgsConstructor
@CrossOrigin

public class PapelController {
    private final PapelService service;
    private final AtorService atorService;
    private final FilmeService filmeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Papel> atividadeComplementares = service.getPapeis();
        return ResponseEntity.ok(atividadeComplementares.stream().map(PapelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Papel> papel = service.getPapelById(id);
        if (!papel.isPresent()) {
            return new ResponseEntity("Papel não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(papel.map(PapelDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody PapelDTO dto) {
        try {
            Papel papel = converter(dto);
            papel = service.salvar(papel);
            return new ResponseEntity(papel, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PapelDTO dto) {
        if (!service.getPapelById(id).isPresent()) {
            return new ResponseEntity("Papel não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Papel papel = converter(dto);
            papel.setId(id);
            service.salvar(papel);
            return ResponseEntity.ok(papel);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Papel> papel = service.getPapelById(id);
        if (!papel.isPresent()) {
            return new ResponseEntity("Papel não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(papel.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Papel converter(PapelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Papel papel = modelMapper.map(dto, Papel.class);
        if (dto.getIdAtor() != null) {
            Optional<Ator> ator = atorService.getAtorById(dto.getIdAtor());
            if (!ator.isPresent()) {
                papel.setAtor(null);
            } else {
                papel.setAtor(ator.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                papel.setFilme(null);
            } else {
                papel.setFilme(filme.get());
            }
        }
        return papel;
    }
}
