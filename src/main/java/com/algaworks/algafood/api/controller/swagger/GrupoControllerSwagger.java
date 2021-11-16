package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerSwagger {

    @ApiOperation("Lista os grupos")
    List<GrupoDTO> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);


    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoDTO atualizar(
            @ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId,
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true) GrupoInput grupoInput);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

}
