package com.algaworks.algafood.api.v1.controller.swagger;

import com.algaworks.algafood.api.v1.dto.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.dto.RestauranteDTO;
import com.algaworks.algafood.api.v1.dto.input.RestauranteInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerSwagger {

    @ApiOperation(value = "Lista restaurantes")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projecao", paramType = "query", type = "string")
    })
    CollectionModel<RestauranteBasicoDTO> listar();


    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteDTO buscar( @ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado"),
    })
    RestauranteDTO adicionar( @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteDTO atualizar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true)
            RestauranteInput restauranteInput);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")})
    void ativarMultiplos(List<Long> restaurantesIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")})
    void inativarMultiplos(List<Long> restaurantesIds);


    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> abrir( @ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fechar( @ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);
}
