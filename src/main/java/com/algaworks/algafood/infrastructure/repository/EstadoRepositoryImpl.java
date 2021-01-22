package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Estado> listar() {
		TypedQuery<Estado> query = entityManager.createQuery("from Estado", Estado.class);
		return query.getResultList();
	}

	@Override
	public Estado buscar(Long id) {
		Estado estado = entityManager.find(Estado.class, id);
		return estado;
	}
	
	@Transactional
	@Override
	public Estado salvar(Estado estado) {
		return entityManager.merge(estado); 
	}
	
	@Transactional
	@Override
	public void remover(Long id) {
		
		Estado estado = buscar(id);
		if(estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		entityManager.remove(estado);
		
	}
	
	
	
}
