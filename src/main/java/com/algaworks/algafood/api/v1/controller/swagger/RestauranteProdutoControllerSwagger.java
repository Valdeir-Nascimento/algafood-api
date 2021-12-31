package com.algaworks.algafood.api.v1.controller.swagger;

import com.algaworks.algafood.api.v1.dto.ProdutoDTO;
import com.algaworks.algafood.api.v1.dto.input.ProdutoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerSwagger {

    @ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<ProdutoDTO> listar(
            @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", example = "false", defaultValue = "false") Boolean incluirInativos,
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);


    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProdutoDTO buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);


    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProdutoDTO adicionar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProdutoInput produtoInput) ;


    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProdutoDTO atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", required = true) ProdutoInput produtoInput);

}
