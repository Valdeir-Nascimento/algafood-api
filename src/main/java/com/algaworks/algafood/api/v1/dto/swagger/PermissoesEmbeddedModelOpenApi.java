package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.PermissaoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("PermissoesEmbeddedDTO")
@Getter
@Setter
public class PermissoesEmbeddedModelOpenApi {

    private List<PermissaoDTO> permissoes;

}
