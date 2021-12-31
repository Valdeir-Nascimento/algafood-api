package com.algaworks.algafood.api.v1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Setter
@Getter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;
    @ApiModelProperty(example = "Minas Gerais")
    private String nome;

}
