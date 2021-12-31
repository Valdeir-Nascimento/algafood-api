package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.GrupoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("GruposEmbeddedDTO")
@Setter
@Getter
public class GruposEmbeddedDTOSwagger {

    private List<GrupoDTO> grupos;

}
