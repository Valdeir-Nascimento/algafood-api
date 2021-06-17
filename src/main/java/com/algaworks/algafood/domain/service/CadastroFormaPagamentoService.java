package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    public static final String MSG_FORMA_DE_PAGEMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long id) {

        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_DE_PAGEMENTO_EM_USO, id));
        }
    }

    public FormaPagamento buscarOuFalhar(Long id) {
        return formaPagamentoRepository
                .findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(id));
    }

}
