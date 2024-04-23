package com.example.safapi.api.dto;
import com.example.safapi.model.entity.Avaliacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {

    private Long id;
    private Double nota;
    private String comentario;
    private Long idUsuario;
    private Long idFilme;

    public static AvaliacaoDTO create(Avaliacao avaliacao) {
        ModelMapper modelMapper = new ModelMapper();
        AvaliacaoDTO dto = modelMapper.map(avaliacao, AvaliacaoDTO.class);
        return dto;
    }
}
