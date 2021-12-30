package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.EstadoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("EstadosEmbeddedModel")
public class EstadosEmbeddedDTOSwagger {

    private List<EstadoDTO> estados;

}
