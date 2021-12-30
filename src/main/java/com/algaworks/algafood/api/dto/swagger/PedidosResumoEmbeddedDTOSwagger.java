package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("PedidosResumoEmbeddedDTO")
public class PedidosResumoEmbeddedDTOSwagger {

    private List<PedidoResumoDTO> pedidos;

}
