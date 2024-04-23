package com.example.safapi.api.dto;
import com.example.safapi.model.entity.FilmeGenero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeGeneroDTO {

    private Long id;
    private Long idGenero;
    private Long idFilme;

    public static FilmeGeneroDTO create(FilmeGenero filmeGenero) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeGeneroDTO dto = modelMapper.map(filmeGenero, FilmeGeneroDTO.class);
        return dto;
    }
}
