package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.controller.swagger.CozinhaControllerSwagger;
import com.algaworks.algafood.api.v1.dto.CozinhaDTO;
import com.algaworks.algafood.api.v1.dto.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerSwagger {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cozinhaService;
    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;
    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;
    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        log.info("Consultando cozinhas com {} registros...", pageable.getPageSize());
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaDTOAssembler);
        return cozinhasPagedModel;
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
        return cozinhaDTOAssembler.toModel(cozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaDTOAssembler.toModel(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(
            @PathVariable("cozinhaId") Long cozinhaId,
            @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
        return cozinhaDTOAssembler.toModel(cozinhaAtual);

    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("cozinhaId") Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }
}
