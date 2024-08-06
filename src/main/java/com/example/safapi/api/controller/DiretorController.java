package com.example.safapi.api.controller;
import com.example.safapi.api.dto.DiretorDTO;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Diretor;
import com.example.safapi.service.DiretorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/diretores")
@RequiredArgsConstructor
@CrossOrigin

public class DiretorController {
    private final DiretorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Diretor> diretores = service.getDiretores();
        return ResponseEntity.ok(diretores.stream().map(DiretorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Diretor> diretor = service.getDiretorById(id);
        if (!diretor.isPresent()) {
            return new ResponseEntity("Diretor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(diretor.map(DiretorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody DiretorDTO dto) {
        try {
            Diretor diretor = converter(dto);
            diretor = service.salvar(diretor);
            return new ResponseEntity(diretor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DiretorDTO dto) {
        if (!service.getDiretorById(id).isPresent()) {
            return new ResponseEntity("Diretor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Diretor diretor = converter(dto);
            diretor.setId(id);
            service.salvar(diretor);
            return ResponseEntity.ok(diretor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Diretor> ator = service.getDiretorById(id);
        if (!ator.isPresent()) {
            return new ResponseEntity("Diretor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ator.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Diretor converter(DiretorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Diretor diretor = modelMapper.map(dto, Diretor.class);
        return diretor;
    }
}
