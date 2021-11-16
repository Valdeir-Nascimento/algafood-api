package com.algaworks.algafood.api.controller.swagger;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerSwagger {

    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<FormaPagamentoDTO> listar( @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    void desvincularFormaPagamento(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    void vincularFormaPagamento(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
