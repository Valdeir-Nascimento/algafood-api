package com.algaworks.algafood.api.v1.assembler;

import java.util.Collection;

import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public FormaPagamentoDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);
        formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        return formaPagamentoModel;
    }


    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToFormasPagamento());
    }

}
