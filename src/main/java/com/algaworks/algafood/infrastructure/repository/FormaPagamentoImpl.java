package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoImpl implements FormaPagamentoRepository{
	
	@PersistenceContext
	private EntityManager entityManger;
	
	@Override
	public List<FormaPagamento> listar() {
		TypedQuery<FormaPagamento> query = entityManger
				.createQuery("from FormaPagamento", FormaPagamento.class); 
		return query.getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return entityManger.find(FormaPagamento.class, id);
	}
	
	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento pagamento) {
		return entityManger.merge(pagamento);
	}
	
	@Transactional
	@Override
	public void remover(FormaPagamento pagamento) {
		pagamento = buscar(pagamento.getId());
		entityManger.remove(pagamento);
	}

}
