package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.PermissaoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerSwagger {

    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    CollectionModel<PermissaoDTO> listar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);


    @ApiOperation("Desassociação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada",
                    response = Problem.class)
    })
    ResponseEntity<Void> desvincular(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId);


    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada",
                    response = Problem.class)
    })
    ResponseEntity<Void> vincular(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId);

}
