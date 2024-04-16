package com.example.safapi.api.dto;

import com.example.safapi.model.entity.Diretor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiretorDTO {

    private Long id;
    private String nome;
    private Date dataNascimento;
    private Date dataInicioCarreira;

    public static DiretorDTO create(Diretor diretor) {
        ModelMapper modelMapper = new ModelMapper();
        DiretorDTO dto = modelMapper.map(diretor, DiretorDTO.class);
        dto.dataInicioCarreira = diretor.getDataInicioCarreira();
        return dto;
    }
}
