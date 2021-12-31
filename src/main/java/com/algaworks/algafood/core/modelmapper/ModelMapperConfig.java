package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.v1.dto.EnderecoDTO;
import com.algaworks.algafood.api.v1.dto.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        var enderecoToEnderecoModelTypeMap = modelMapper
                .createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDestino, valor) -> enderecoModelDestino.getCidade().setEstado(valor));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
        return modelMapper;
    }

}
