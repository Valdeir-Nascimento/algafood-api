package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.v1.controller.swagger.PedidoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.PedidoDTO;
import com.algaworks.algafood.api.v1.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.dto.input.PedidoInput;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecification;
import com.google.common.collect.ImmutableMap;
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

@RestController
@RequestMapping(value = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerSwagger {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;
    @Autowired
    private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;
    @Autowired
    private PedidoDTOAssembler pedidoDTOAssembler;
    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;
    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.Pedidos.PodePesquisar
    @GetMapping
    public PagedModel<PedidoResumoDTO> pesquisar(@PageableDefault(size = 10) Pageable pageable, PedidoFilter filtro) {
        Pageable pageableTraduzido = traduzirPeageable(pageable);
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecification.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);
        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoDTOAssembler);
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigoPedido}")
    public PedidoResumoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        return pedidoResumoDTOAssembler.toModel(pedido);
    }

    @CheckSecurity.Pedidos.PodeCriar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());

            //TODO: Pegar usuário logado
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);
            return pedidoDTOAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPeageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "cliente.nome", "cliente.nome",
                "valorTotal", "valorTotal"

        );

        return PageableTranslator.translate(pageable, mapeamento);
    }

}
