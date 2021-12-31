package com.algaworks.algafood.api.v1.dto.swagger;

import com.algaworks.algafood.api.v1.dto.UsuarioDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("UsuariosEmbeddedModel")
public class UsuariosEmbeddedDTOSwagger {

    private List<UsuarioDTO> usuarios;

}
