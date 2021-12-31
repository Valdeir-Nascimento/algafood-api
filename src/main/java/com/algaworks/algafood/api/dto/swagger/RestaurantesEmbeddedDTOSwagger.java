package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.RestauranteBasicoDTO;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel("RestaurantesEmbeddedDTO")
public class RestaurantesEmbeddedDTOSwagger {

    private List<RestauranteBasicoDTO> restaurantes;

}
