package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.PermissaoDTO;
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
