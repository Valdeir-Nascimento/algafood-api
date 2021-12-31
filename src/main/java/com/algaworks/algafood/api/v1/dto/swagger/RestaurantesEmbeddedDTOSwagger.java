package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.RestauranteBasicoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("RestaurantesEmbeddedDTO")
public class RestaurantesEmbeddedDTOSwagger {

    private List<RestauranteBasicoDTO> restaurantes;

}
