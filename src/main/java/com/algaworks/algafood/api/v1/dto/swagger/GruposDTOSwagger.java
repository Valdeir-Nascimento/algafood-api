package com.algaworks.algafood.api.v1.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@ApiModel("GruposDTO")
@Setter
@Getter
public class GruposDTOSwagger {

    private GruposEmbeddedDTOSwagger _embedded;
    private Links _links;

}
