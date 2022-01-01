package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.CidadeDTOAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.dto.CidadeDTOV2;
import com.algaworks.algafood.api.v2.dto.input.CidadeInputV2;
import com.algaworks.algafood.core.util.ResourceUriUtil;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    @Autowired
    private CadastroCidadeService cidadeService;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeDTOAssemblerV2 cidadeDTOAssembler;
    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeDTOV2> listar() {
        return cidadeDTOAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @PostMapping
    public CidadeDTOV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cidadeService.salvar(cidade);
            CidadeDTOV2 cidadeDTO = cidadeDTOAssembler.toModel(cidade);
            ResourceUriUtil.addUriInResponseHeader(cidadeDTO.getIdCidade());
            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTOV2 buscar(@PathVariable("cidadeId") Long cidadeId) {
        return cidadeDTOAssembler.toModel(cidadeService.buscarOuFalhar(cidadeId));
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTOV2 atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

        try {
            cidadeInputDisassembler.copyDomainObject(cidadeInput, cidadeAtual);
            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeDTOAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
