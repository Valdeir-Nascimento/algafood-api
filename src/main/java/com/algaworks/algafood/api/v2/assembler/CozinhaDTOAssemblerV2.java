package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.dto.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.links.AlgaLinksV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTOV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CozinhaDTOAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaDTOV2.class);
    }

    @Override
    public CozinhaDTOV2 toModel(Cozinha cozinha) {
        CozinhaDTOV2 cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDTO);
        cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));
        return cozinhaDTO;
    }

    @Override
    public CollectionModel<CozinhaDTOV2> toCollectionModel(Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities);
    }
}
