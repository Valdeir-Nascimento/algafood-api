package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssember;
import com.algaworks.algafood.api.v1.controller.swagger.PermissaoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerSwagger {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoDTOAssember permissaoDTOAssember;

    @Override
    @GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        return permissaoDTOAssember.toCollectionModel(todasPermissoes);
    }

}
