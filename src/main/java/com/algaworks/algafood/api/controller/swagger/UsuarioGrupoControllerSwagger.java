package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerSwagger {


    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    CollectionModel<GrupoDTO> listar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);


    @ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    void desvincular(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);


    @ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    void vincular(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);


}
