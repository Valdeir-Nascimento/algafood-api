package com.algaworks.algafood.api.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@ApiModel("UsuariosDTO")
@Setter
@Getter
public class UsuariosDTOSwagger {
    private UsuariosEmbeddedDTOSwagger _embedded;
    private Links _links;
}
