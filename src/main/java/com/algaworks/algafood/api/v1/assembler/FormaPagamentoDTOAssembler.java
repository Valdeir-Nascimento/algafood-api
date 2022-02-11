package com.algaworks.algafood.api.v1.assembler;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;
    @Autowired
    private AlgaSecurity algaSecurity;  

    public FormaPagamentoDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
        
        modelMapper.map(formaPagamento, formaPagamentoDTO);
        if (algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }
        
        return formaPagamentoDTO;
    }


    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> entities) {
        CollectionModel<FormaPagamentoDTO> list = super.toCollectionModel(entities);
        
        if (algaSecurity.podeConsultarFormasPagamento()) {
            list.add(algaLinks.linkToFormasPagamento());
        }
            
        return list;
    }

}
