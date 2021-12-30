package com.algaworks.algafood.api.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@Setter
@Getter
@ApiModel("EstadosDTO")
public class EstadosDTOSwagger {

    private EstadosEmbeddedDTOSwagger _embedded;
    private Links _links;

}
