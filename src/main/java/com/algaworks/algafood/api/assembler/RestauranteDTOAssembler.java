package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteDTOAssembler() {
        super(RestauranteController.class, RestauranteDTO.class);
    }

    public RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        return restauranteModel;
    }

    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
    }
}



