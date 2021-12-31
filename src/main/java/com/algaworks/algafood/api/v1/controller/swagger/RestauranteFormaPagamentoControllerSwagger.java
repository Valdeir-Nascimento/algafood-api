package com.algaworks.algafood.api.v1.controller.swagger;

import com.algaworks.algafood.api.v1.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerSwagger {

    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<FormaPagamentoDTO> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> desvincularFormaPagamento(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> vincularFormaPagamento(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
