package com.example.safapi.api.controller;
import com.example.safapi.api.dto.AtorDTO;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Ator;
import com.example.safapi.service.AtorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/atores")
@RequiredArgsConstructor
@CrossOrigin

public class AtorController {
    private final AtorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Ator> atores = service.getAtores();
        return ResponseEntity.ok(atores.stream().map(AtorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Ator> ator = service.getAtorById(id);
        if (!ator.isPresent()) {
            return new ResponseEntity("Ator não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ator.map(AtorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AtorDTO dto) {
        try {
            Ator ator = converter(dto);
            ator = service.salvar(ator);
            return new ResponseEntity(ator, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AtorDTO dto) {
        if (!service.getAtorById(id).isPresent()) {
            return new ResponseEntity("Ator não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Ator ator = converter(dto);
            ator.setId(id);
            service.salvar(ator);
            return ResponseEntity.ok(ator);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Ator> ator = service.getAtorById(id);
        if (!ator.isPresent()) {
            return new ResponseEntity("Ator não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ator.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Ator converter(AtorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Ator ator = modelMapper.map(dto, Ator.class);
        return ator;
    }


}
