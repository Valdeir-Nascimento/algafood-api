package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.controller.swagger.EstadoControllerSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerSwagger {

    @Autowired
    private CadastroEstadoService estadoService;
    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;
    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public CollectionModel<EstadoDTO> listar() {
        return estadoDTOAssembler.toCollectionModel(estadoService.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable("estadoId") Long estadoId) {
        return estadoDTOAssembler.toModel(estadoService.buscarOuFalhar(estadoId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        return estadoDTOAssembler.toModel(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
        estadoAtual = estadoService.salvar(estadoAtual);
        return estadoDTOAssembler.toModel(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }


}
