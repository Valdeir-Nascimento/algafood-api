package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.ProdutoDTO;
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
