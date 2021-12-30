package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel("FormasPagamentoEmbeddedDTO")
public class FormasPagamentoEmbeddedDTOSwagger {

    private List<FormaPagamentoDTO> formasPagamento;

}
