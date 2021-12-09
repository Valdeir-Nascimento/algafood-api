package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        pedidoDTO.add(algaLinks.linkToPedidos());

        if (pedido.podeSerConfirmado()) {
            pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedidoDTO.getCodigo(), "confirmar   "));
        }

        if (pedido.podeSerEntregue()) {
            pedidoDTO.add(algaLinks.linkToEntregaPedido(pedidoDTO.getCodigo(), "entregar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedidoDTO.getCodigo(), "cancelar"));
        }

        pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoDTO.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoDTO.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduto(pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoDTO;
    }

    public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
