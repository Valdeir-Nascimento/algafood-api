package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.api.v1.dto.MensagemDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailService extends SmtpEnvioEmailService{

    @Override
    public void enviar(MensagemDTO mensagem) {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
