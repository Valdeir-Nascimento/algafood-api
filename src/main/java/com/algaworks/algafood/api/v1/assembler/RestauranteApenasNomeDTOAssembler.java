package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.dto.RestauranteApenasNomeDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteApenasNomeDTOAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeDTO.class);
    }

    @Override
    public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
        RestauranteApenasNomeDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
    }

}
