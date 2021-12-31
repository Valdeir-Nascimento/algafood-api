package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.api.v1.dto.MensagemDTO;
import com.algaworks.algafood.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage criarMimeMessage(MensagemDTO mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getDestinatario());
        return mimeMessage;

    }
}
