package com.algaworks.algafood.api.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@ApiModel("ProdutosDTO")
@Setter
@Getter
public class ProdutosDTOSwagger {

    private ProdutosEmbeddedDTOSwagger _embedded;
    private Links _links;

}
