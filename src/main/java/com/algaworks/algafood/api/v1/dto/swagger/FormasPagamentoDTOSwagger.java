package com.algaworks.algafood.api.v1.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@Setter
@Getter
@ApiModel("FormasPagamentoDTO")
public class FormasPagamentoDTOSwagger {

    private FormasPagamentoEmbeddedDTOSwagger _embedded;
    private Links _links;

}
