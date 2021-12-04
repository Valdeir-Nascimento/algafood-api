package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.input.EstadoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Estados")
public interface EstadoControllerSwagger {

    @ApiOperation("Lista os estados")
    CollectionModel<EstadoDTO> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoDTO buscar(Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({@ApiResponse(code = 201, message = "Estado cadastrado")})
    EstadoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoDTO atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId,
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true) EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

}
