package com.example.safapi.api.controller;
import com.example.safapi.api.dto.DirecaoDTO;
import com.example.safapi.model.entity.Direcao;
import com.example.safapi.model.entity.Diretor;
import com.example.safapi.model.entity.Filme;
import com.example.safapi.service.DirecaoService;
import com.example.safapi.service.DiretorService;
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
@RequestMapping("/api/v1/direcoes")
@RequiredArgsConstructor
@CrossOrigin

public class DirecaoController {
    private final DirecaoService service;
    private final DiretorService diretorService;
    private final FilmeService filmeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Direcao> direcoes = service.getDirecoes();
        return ResponseEntity.ok(direcoes.stream().map(DirecaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Direcao> direcao = service.getDirecaoById(id);
        if (!direcao.isPresent()) {
            return new ResponseEntity("Direção não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(direcao.map(DirecaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody DirecaoDTO dto) {
        try {
            Direcao direcao = converter(dto);
            direcao = service.salvar(direcao);
            return new ResponseEntity(direcao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DirecaoDTO dto) {
        if (!service.getDirecaoById(id).isPresent()) {
            return new ResponseEntity("Avaliação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Direcao direcao = converter(dto);
            direcao.setId(id);
            service.salvar(direcao);
            return ResponseEntity.ok(direcao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Direcao> direcao = service.getDirecaoById(id);
        if (!direcao.isPresent()) {
            return new ResponseEntity("Direção não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(direcao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Direcao converter(DirecaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Direcao direcao = modelMapper.map(dto, Direcao.class);
        if (dto.getIdDiretor() != null) {
            Optional<Diretor> diretor = diretorService.getDiretorById(dto.getIdDiretor());
            if (!diretor.isPresent()) {
                direcao.setDiretor(null);
            } else {
                direcao.setDiretor(diretor.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                direcao.setFilme(null);
            } else {
                direcao.setFilme(filme.get());
            }
        }
        return direcao;
    }
}
