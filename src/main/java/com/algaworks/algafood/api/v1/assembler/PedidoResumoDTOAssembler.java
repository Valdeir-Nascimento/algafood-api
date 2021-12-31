package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoResumoDTOAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoResumoDTO);
        pedidoResumoDTO.add(algaLinks.linkToPedidos("pedidos"));
        pedidoResumoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoResumoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        return pedidoResumoDTO;
    }


}
