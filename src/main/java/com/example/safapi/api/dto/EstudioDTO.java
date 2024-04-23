package com.example.safapi.api.dto;
import com.example.safapi.model.entity.Estudio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioDTO {

    private Long id;
    private String nome;
    private String pais;
    private Date dataFundacao;

    public static EstudioDTO create(Estudio estudio) {
        ModelMapper modelMapper = new ModelMapper();
        EstudioDTO dto = modelMapper.map(estudio, EstudioDTO.class);
        return dto;
    }
}
