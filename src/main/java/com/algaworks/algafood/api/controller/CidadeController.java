package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
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
        return cidadeRepository.save(cidade);
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long cidadeId) {
        Cidade cidade = cidadeService.findById(cidadeId);
        if (cidade != null) {
            return ResponseEntity.ok(cidade);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade) {

        try {
            Cidade cidadeAtual = cidadeService.findById(cidadeId);
            if (cidadeAtual != null) {
                BeanUtils.copyProperties(cidade, cidadeAtual, "id");
                cidadeAtual = cidadeService.save(cidadeAtual);
                return ResponseEntity.ok(cidadeAtual);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeId) {
        try {
            cidadeService.deleteById(cidadeId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }


}
