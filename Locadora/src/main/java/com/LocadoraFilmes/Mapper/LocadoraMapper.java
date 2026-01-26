package com.LocadoraFilmes.Mapper;

import com.LocadoraFilmes.dto.LocadoraDTO;
import com.LocadoraFilmes.model.Locadora;

public class LocadoraMapper {
    
public static LocadoraDTO toDTO(Locadora l) {
return new LocadoraDTO(
    l.getId(),
    l.getNome(),
    l.getGenero().getNome(),
    l.getPlataforma().getNome()
);
}

}
