package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.controller.swagger.CozinhaControllerSwagger;
import com.algaworks.algafood.api.v1.dto.CozinhaDTO;
import com.algaworks.algafood.api.v1.dto.input.CozinhaInput;
import com.algaworks.algafood.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.algaworks.algafood.api.v2.dto.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.dto.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cozinhaService;
    @Autowired
    private CozinhaDTOAssemblerV2 cozinhaDTOAssembler;
    @Autowired
    private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;
    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaDTOV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        PagedModel<CozinhaDTOV2> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaDTOAssembler);
        return cozinhasPagedModel;
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTOV2 buscar(@PathVariable Long cozinhaId) {
        return cozinhaDTOAssembler.toModel(cozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTOV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaDTOAssembler.toModel(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTOV2 atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
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
