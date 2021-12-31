package com.algaworks.algafood.api.v1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteDTO extends RepresentationModel<RestauranteDTO> {

    @ApiModelProperty(example = "1")
//    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
//    @JsonView(RestauranteView.Resumo.class)
    private String nome;

    @ApiModelProperty(example = "12.00")
//    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

//    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDTO cozinha;

    private Boolean ativo;
    private EnderecoDTO endereco;

}
