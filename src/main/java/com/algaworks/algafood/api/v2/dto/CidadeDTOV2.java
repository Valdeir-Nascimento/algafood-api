package com.algaworks.algafood.api.v2.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2> {

    @ApiModelProperty(example = "1")
    private Long idCidade;

    @ApiModelProperty(example = "Uberlândia")
    private String nomeCidade;
    private Long idEstado;
    private String nomeEstado;

}
