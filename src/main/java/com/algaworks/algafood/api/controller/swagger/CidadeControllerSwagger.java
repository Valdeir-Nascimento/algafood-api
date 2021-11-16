package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerSwagger {


    @ApiOperation("Lista as cidades")
    List<CidadeDTO> listar();

    @ApiOperation("Cadastra uma cidade")
    CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);


    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);


    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
    })
    CidadeDTO atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
            @ApiParam(name = "corpo", value = "ID de uma cidade", example = "1", required = true) CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    void remover(@ApiParam(name = "corpo", value = "ID de uma cidade", example = "1", required = true) Long cidadeId);




}
