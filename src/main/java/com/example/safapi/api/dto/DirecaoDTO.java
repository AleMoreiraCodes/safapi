package com.example.safapi.api.dto;
import com.example.safapi.model.entity.Direcao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirecaoDTO {

    private Long id;
    private Long idDiretor;
    private Long idFilme;

    public static DirecaoDTO create(Direcao direcao) {
        ModelMapper modelMapper = new ModelMapper();
        DirecaoDTO dto = modelMapper.map(direcao, DirecaoDTO.class);
        return dto;
    }
}
