package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoDTOAssembler  extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public FotoProdutoDTOAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    @Override
    public FotoProdutoDTO toModel(FotoProduto foto) {
        FotoProdutoDTO fotoProdutoDTO = modelMapper.map(foto, FotoProdutoDTO.class);
        fotoProdutoDTO.add(algaLinks.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));
        fotoProdutoDTO.add(algaLinks.linkToProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        return fotoProdutoDTO;
    }

    public List<FotoProdutoDTO> toCollectionDTO(List<FotoProduto> fotos){
        return fotos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
