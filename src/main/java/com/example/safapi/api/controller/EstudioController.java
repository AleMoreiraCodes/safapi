package com.example.safapi.api.controller;
import com.example.safapi.api.dto.EstudioDTO;
import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Estudio;
import com.example.safapi.service.EstudioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/estudios")
@RequiredArgsConstructor
@CrossOrigin

public class EstudioController {
    private final EstudioService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Estudio> estudios = service.getEstudios();
        return ResponseEntity.ok(estudios.stream().map(EstudioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estudio> ator = service.getEstudioById(id);
        if (!ator.isPresent()) {
            return new ResponseEntity("Estudio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ator.map(EstudioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody EstudioDTO dto) {
        try {
            Estudio estudio = converter(dto);
            estudio = service.salvar(estudio);
            return new ResponseEntity(estudio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EstudioDTO dto) {
        if (!service.getEstudioById(id).isPresent()) {
            return new ResponseEntity("Estudio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Estudio estudio = converter(dto);
            estudio.setId(id);
            service.salvar(estudio);
            return ResponseEntity.ok(estudio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Estudio> estudio = service.getEstudioById(id);
        if (!estudio.isPresent()) {
            return new ResponseEntity("Estudio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(estudio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Estudio converter(EstudioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estudio estudio = modelMapper.map(dto, Estudio.class);
        return estudio;
    }
}
