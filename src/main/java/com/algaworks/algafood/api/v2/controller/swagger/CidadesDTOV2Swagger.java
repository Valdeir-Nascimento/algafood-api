package com.algaworks.algafood.api.v2.controller.swagger;

import com.algaworks.algafood.api.v2.dto.CidadeDTOV2;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesDTO")
@Getter
@Setter
public class CidadesDTOV2Swagger {

    private CidadesEmbeddedDTOSwagger _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedDTO")
    @Getter
    @Setter
    public class CidadesEmbeddedDTOSwagger {

        private List<CidadeDTOV2> cidades;

    }

}
