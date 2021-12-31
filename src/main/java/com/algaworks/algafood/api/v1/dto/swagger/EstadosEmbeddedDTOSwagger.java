package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.EstadoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("EstadosEmbeddedDTO")
public class EstadosEmbeddedDTOSwagger {

    private List<EstadoDTO> estados;

}
