package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.dto.MensagemDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;
    @Autowired
    private EnvioEmailService envioEmailService;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

        var mensagem = MensagemDTO.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .corpo("O pedido de código <strong>" + pedido.getCodigo() + "</strong> foi confirmado")
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

}
