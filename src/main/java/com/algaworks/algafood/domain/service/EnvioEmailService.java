package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.dto.MensagemDTO;

public interface EnvioEmailService {

    void enviar(MensagemDTO mensagem);

}
