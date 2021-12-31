package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.ProdutoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@ApiModel("ProdutosEmbeddedDTO")
@Setter
@Getter
public class ProdutosEmbeddedDTOSwagger {

    private List<ProdutoDTO> produtos;

}
