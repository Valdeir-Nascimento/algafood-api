package com.algaworks.algafood.api.v1.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Link")
public class LinkDTO {

    private String href;
    private String templated;

}
