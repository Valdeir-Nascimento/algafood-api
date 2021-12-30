package com.algaworks.algafood.api.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@Setter
@Getter
@ApiModel("CozinhasModel")
public class CozinhasDTOSwagger {

    private CozinhaEmbeddedDTOSwagger _embedded;
    private Links _links;
    private PageDTOSwagger page;

}
