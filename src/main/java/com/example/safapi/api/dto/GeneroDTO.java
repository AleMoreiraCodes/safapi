package com.example.safapi.api.dto;

import com.example.safapi.model.entity.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneroDTO {

    private Long id;
    private String nome;
    private String descricao;

    public static GeneroDTO create(Genero genero) {
        ModelMapper modelMapper = new ModelMapper();
        GeneroDTO dto = modelMapper.map(genero, GeneroDTO.class);
        return dto;
    }
}
