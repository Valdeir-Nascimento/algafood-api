package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.api.dto.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cozinhaService;
    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;
    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaDTO> listar() {
        return cozinhaDTOAssembler.toCollectionDTO(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
       return cozinhaDTOAssembler.toDTO(cozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaDTOAssembler.toDTO(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(
            @PathVariable("cozinhaId") Long cozinhaId,
            @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
        return cozinhaDTOAssembler.toDTO(cozinhaAtual);

    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cozinhaId") Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }
}
