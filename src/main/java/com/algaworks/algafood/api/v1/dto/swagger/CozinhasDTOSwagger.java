package com.algaworks.algafood.api.v1.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@Setter
@Getter
@ApiModel("CozinhasDTO")
public class CozinhasDTOSwagger {

    private CozinhaEmbeddedDTOSwagger _embedded;
    private Links _links;
    private PageDTOSwagger page;

}
