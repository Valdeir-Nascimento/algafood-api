package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.dto.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteBasicoDTOAssembler() {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
    }

    @Override
    public RestauranteBasicoDTO toModel(Restaurante restaurante) {
        RestauranteBasicoDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
    }

}
