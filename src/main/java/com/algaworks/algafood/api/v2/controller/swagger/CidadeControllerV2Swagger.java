package com.algaworks.algafood.api.v2.controller.swagger;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.dto.CidadeDTOV2;
import com.algaworks.algafood.api.v2.dto.input.CidadeInputV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerV2Swagger {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeDTOV2> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeDTOV2 buscar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
                    Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeDTOV2 adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
                    CidadeInputV2 cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeDTOV2 atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
                    Long cidadeId,

            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true)
                    CidadeInputV2 cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
                    Long cidadeId);

}
