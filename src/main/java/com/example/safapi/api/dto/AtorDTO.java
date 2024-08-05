package com.example.safapi.api.dto;
import com.example.safapi.model.entity.Ator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtorDTO {
    private Long id;
    private String nome;
    private Date dataNascimento;
    private Date dataInicioCarreira;
    private String nacionalidade;

    public static AtorDTO create(Ator ator) {
        ModelMapper modelMapper = new ModelMapper();
        AtorDTO dto = modelMapper.map(ator, AtorDTO.class);
        return dto;
    }
}
