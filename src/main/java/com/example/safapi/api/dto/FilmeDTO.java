package com.example.safapi.api.dto;
import com.example.safapi.model.entity.Filme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTO {

    private Long id;
    private String titulo;
    private Date dataLancamento;
    private String sinopse;
    private Double duracao;
    private Double imdb;
    private Long idEstudio;

    public static FilmeDTO create(Filme filme) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeDTO dto = modelMapper.map(filme, FilmeDTO.class);
        return dto;
    }
}
