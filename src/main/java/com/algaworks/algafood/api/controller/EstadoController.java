package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService estadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoService.findAll();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable("estadoId") Long estadoId) {

        Estado estado = estadoService.findById(estadoId);
        if (estado != null) {
            return ResponseEntity.ok(estado);
        }

        return ResponseEntity.notFound().build();

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Estado adicionar(@RequestBody Estado estado) {
        return estadoService.save(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody Estado estado) {
        try {
            Estado estadoAtual = estadoService.findById(estadoId);
            if (estadoAtual != null) {
                BeanUtils.copyProperties(estado, estadoAtual, "id");
                estadoAtual = estadoService.save(estadoAtual);
                return ResponseEntity.ok(estadoAtual);
            }
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> excluir(@PathVariable Long estadoId) {
        try {
            estadoService.deleteById(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }

    }


}
