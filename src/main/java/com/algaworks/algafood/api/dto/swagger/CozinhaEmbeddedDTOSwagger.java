package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("CozinhasEmbeddedDTO")
public class CozinhaEmbeddedDTOSwagger {

    private List<CozinhaDTO> cozinhas;

}
