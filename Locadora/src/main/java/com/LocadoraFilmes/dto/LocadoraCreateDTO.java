package com.LocadoraFilmes.dto;

import jakarta.validation.constraints.NotBlank;

public record LocadoraCreateDTO(@NotBlank
String nome,


@NotNull
Long generoId,


@NotNull
Long plataformaId) {
    
}
