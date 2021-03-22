package com.algaworks.algafood.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cidade adicionar(@RequestBody Cidade cidade) {
        try {
            return cidadeService.salvar(cidade);
        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable("cidadeId") Long cidadeId) {
        return cidadeService.buscarOuFalhar(cidadeId);
    }


    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
        try {
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            return cidadeService.salvar(cidadeAtual);
        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
