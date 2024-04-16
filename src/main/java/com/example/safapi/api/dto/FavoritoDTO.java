package com.example.safapi.api.dto;

import com.example.safapi.model.entity.Favorito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {

    private Long id;
    private Long idUsuario;
    private Long idFilme;

    public static FavoritoDTO create(Favorito favorito) {
        ModelMapper modelMapper = new ModelMapper();
        FavoritoDTO dto = modelMapper.map(favorito, FavoritoDTO.class);
        dto.idUsuario = favorito.getUsuario().getId();
        dto.idFilme = favorito.getFilme().getId();
        return dto;
    }
}
