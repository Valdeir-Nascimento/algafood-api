package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.controller.swagger.CidadeControllerSwagger;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.core.util.ResourceUriUtil;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerSwagger {

    @Autowired
    private CadastroCidadeService cidadeService;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;
    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeDTO> listar() {
        return cidadeDTOAssembler.toCollectionDTO(cidadeRepository.findAll());
    }

    @PostMapping
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cidadeService.salvar(cidade);
            CidadeDTO cidadeDTO = cidadeDTOAssembler.toDTO(cidade);

            ResourceUriUtil.addUriInResponseHeader(cidadeDTO.getId());

            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable("cidadeId") Long cidadeId) {
        CidadeDTO cidadeDTO = cidadeDTOAssembler.toDTO(cidadeService.buscarOuFalhar(cidadeId));

        cidadeDTO.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeDTO.getId()))
                .withSelfRel());


        cidadeDTO.add(linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades"));



        cidadeDTO.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeDTO.getEstado().getId()))
                .withSelfRel());

//        cidadeDTO.getEstado().add(linkTo(EstadoController.class)
//                .slash(cidadeDTO.getEstado().getId())
//                .withSelfRel()
//        );


        return cidadeDTO;
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(
            @PathVariable("cidadeId") Long cidadeId,
            @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
        try {
            cidadeInputDisassembler.copyDomainObject(cidadeInput, cidadeAtual);
            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeDTOAssembler.toDTO(cidadeAtual);
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
