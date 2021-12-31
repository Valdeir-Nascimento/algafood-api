package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.dto.PermissaoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class PermissaoDTOAssember implements RepresentationModelAssembler<Permissao, PermissaoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    public PermissaoDTO toModel(Permissao permissao) {
        PermissaoDTO permissaoDTO = modelMapper.map(permissao, PermissaoDTO.class);
        return permissaoDTO;
    }

    @Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(algaLinks.linkToPermissoes());
    }
}
