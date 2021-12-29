package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;

    public ProdutoDTOAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    public ProdutoDTO toModel(Produto produto) {
        ProdutoDTO produtoDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoDTO);
        produtoDTO.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        produtoDTO.add(algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
        return produtoDTO;
    }

    public List<ProdutoDTO> toCollectionDTO(Collection<Produto> produtos) {
        return produtos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
