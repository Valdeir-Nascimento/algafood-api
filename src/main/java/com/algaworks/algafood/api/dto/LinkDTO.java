package com.algaworks.algafood.api.dto;

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
