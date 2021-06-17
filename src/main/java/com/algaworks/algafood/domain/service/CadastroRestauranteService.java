package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroCozinhaService cozinhaService;
    @Autowired
    private CadastroCidadeService cidadeService;
    @Autowired
    private CadastroFormaPagamentoService pagamentoService;
    @Autowired
    private CadastroUsuarioService usuarioService;


    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    @Transactional
    public void ativar(Long restauranteId) {
        //Não precisa chamar o metodo salvar, pois essa instancia esta sendo gerenciado pelo JPA
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.inativar();
    }

    @Transactional
    public void desvincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        //Desvincular forma de pagamento do restauratante
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void vincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        //Vincular forma de pagamento do restauratante
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.fechar();
    }

    @Transactional
    public void vincularResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        restaurante.adicionarReponsavel(usuario);
    }

    @Transactional
    public void desvincularResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        restaurante.removerResponsavel(usuario);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository
                .findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

}
