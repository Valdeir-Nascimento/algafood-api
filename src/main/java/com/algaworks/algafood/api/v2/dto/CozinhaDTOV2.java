package com.algaworks.algafood.api.v2.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel("CozinhaDTO")
@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDTOV2 extends RepresentationModel<CozinhaDTOV2> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    private String nome;
}
