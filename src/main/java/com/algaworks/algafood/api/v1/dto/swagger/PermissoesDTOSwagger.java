package com.algaworks.algafood.api.v1.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@Setter
@Getter
@ApiModel("PermissoesDTO")
public class PermissoesDTOSwagger {
    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;
}
