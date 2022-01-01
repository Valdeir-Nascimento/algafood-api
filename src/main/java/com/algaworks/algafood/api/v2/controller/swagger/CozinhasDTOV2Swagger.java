package com.algaworks.algafood.api.v2.controller.swagger;

import com.algaworks.algafood.api.v1.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasDTO")
@Setter
@Getter
public class CozinhasDTOV2Swagger {

    private CozinhasEmbeddedDTOSwagger _embedded;
    private Links _links;
    private PageDTOV2Swagger page;

    @ApiModel("CozinhasEmbeddedDTO")
    @Getter
    @Setter
    public class CozinhasEmbeddedDTOSwagger {

        private List<CozinhaDTO> cozinhas;

    }

}
