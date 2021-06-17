package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.dto.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelmapper = new ModelMapper();
        var enderecoToEnderecoModelTypeMap = modelmapper
                .createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDestino, valor) -> enderecoModelDestino.getCidade().setEstado(valor));
        return modelmapper;
    }

}
