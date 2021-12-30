package com.algaworks.algafood.api.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@Setter
@Getter
@ApiModel("CidadesDTO")
public class CidadesDTOSwagger {

    private CidadeEmbeddedDTOSwagger _embedded;
    private Links _links;

}
