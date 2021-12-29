package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerSwagger {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoDTO> listar();

}
