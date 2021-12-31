package com.algaworks.algafood.api.v1.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@ApiModel("PedidosResumoDTO")
@Getter
@Setter
public class PedidosResumoDTOSwagger {

    private PedidosResumoEmbeddedDTOSwagger _embedded;
    private Links _links;
    private PageDTOSwagger page;

}
