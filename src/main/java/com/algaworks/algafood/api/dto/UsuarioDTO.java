package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
}
