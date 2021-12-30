package com.algaworks.algafood.api.dto.swagger;

import com.algaworks.algafood.api.dto.GrupoDTO;
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
