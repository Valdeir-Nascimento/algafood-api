package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.controller.swagger.FormaPagamentoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.dto.input.FormaPagamentoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/formas-pagamento",  produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerSwagger {

    @Autowired
    private CadastroFormaPagamentoService pagamentoService;
    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formaPagamentoList = pagamentoService.listar();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoDTOAssembler.toCollectionModel(formaPagamentoList));
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        formaPagamento = pagamentoService.salvar(formaPagamento);
        return formaPagamentoDTOAssembler.toModel(formaPagamento);
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(formaPagamentoId);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }
        FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagamentoId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoDTOAssembler.toModel(formaPagamento));
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(
            @PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = pagamentoService.buscarOuFalhar(formaPagamentoId);
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        formaPagamentoAtual = pagamentoService.salvar(formaPagamentoAtual);
        return formaPagamentoDTOAssembler.toModel(formaPagamentoAtual);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        pagamentoService.excluir(formaPagamentoId);
    }

}
