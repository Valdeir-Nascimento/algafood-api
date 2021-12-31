package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.CidadeDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("CidadesEmbeddedDTO")
public class CidadeEmbeddedDTOSwagger {

    private List<CidadeDTO> cidades;

}
