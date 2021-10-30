package com.algaworks.algafood.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

@Builder
@Getter
public class MensagemDTO {

    @Singular
    private Set<String> destinatarios;
    @NonNull
    private String assunto;
    @NonNull
    private String corpo;

}
