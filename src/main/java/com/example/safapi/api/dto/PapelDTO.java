package com.example.safapi.api.dto;
import com.example.safapi.model.entity.Papel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PapelDTO {

    private Long id;
    private String nome;
    private Long idAtor;
    private Long idFilme;

    public static PapelDTO create(Papel papel) {
        ModelMapper modelMapper = new ModelMapper();
        PapelDTO dto = modelMapper.map(papel, PapelDTO.class);
        return dto;
    }
}
