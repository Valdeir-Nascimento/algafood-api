package com.algaworks.algafood.api.dto.swagger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@ApiModel("RestaurantesBasicoDTO")
@Getter
@Setter
public class RestaurantesBasicoDTOSwagger {

    private RestaurantesEmbeddedDTOSwagger _embedded;
    private Links _links;

}
