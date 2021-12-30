package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.LinkDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinksDTOSwagger {

    private LinkDTO rel;


}
