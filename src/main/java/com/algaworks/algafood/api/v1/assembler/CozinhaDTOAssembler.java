package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.dto.CozinhaDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CozinhaDTOAssembler() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDTO);
        cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));
        return cozinhaDTO;
    }

    @Override
    public CollectionModel<CozinhaDTO> toCollectionModel(Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities);
    }
}
