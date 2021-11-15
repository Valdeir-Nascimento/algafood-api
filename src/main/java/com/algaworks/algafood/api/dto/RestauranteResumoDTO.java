package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteResumoDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

}
